#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <string.h>
#include <netinet/udp.h>
#include <arpa/inet.h>
#include <netdb.h>

#include <liburing.h>

int main(int argc, char *argv[])
{
  struct io_uring ring;
  io_uring_queue_init(8, &ring, 0);
  int fd = socket(AF_INET, SOCK_STREAM, 0);
  struct addrinfo * ai;
  getaddrinfo("httpbin.org", "80", NULL, &ai);
  connect(fd, ai->ai_addr, ai->ai_addrlen);
  char* req = "GET /get HTTP/1.1\nHost: httpbin.org\n\n";
  
  while (true) {
    struct io_uring_sqe *sqe = io_uring_get_sqe(&ring);
    struct io_uring_cqe *cqe;
    io_uring_prep_send(sqe, fd, req, strlen(req), MSG_NOSIGNAL);
    
    int ret = io_uring_submit(&ring);
    if (ret != 1) {
      perror("io_uring_submit");
      return 1;
    }
    
    ret = io_uring_wait_cqe(&ring, &cqe);
    if (ret < 0) {
      perror("io_uring_wait_cqe");
      return 1;
    }
    
    io_uring_cqe_seen(&ring, cqe);
  }
}

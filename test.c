#include <assert.h>
#include <stdio.h>
#include <sys/socket.h>
#include <liburing.h>

int main() {
  struct io_uring ring;
  assert(io_uring_queue_init(1, &ring, 0) == 0);

  assert(socket(AF_INET, SOCK_STREAM, 0) >= 0);

  struct io_uring_sqe *sqe = io_uring_get_sqe(&ring);
  assert(sqe != NULL);
  io_uring_prep_socket(sqe, AF_INET, SOCK_DGRAM, 0, 0);

  io_uring_submit(&ring);
  
  struct io_uring_cqe *cqe;
  io_uring_wait_cqe(&ring, &cqe);

  printf("%d\n", cqe->res);
}

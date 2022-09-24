clang -I/home/linuxbrew/.linuxbrew/include -L/home/linuxbrew/.linuxbrew/lib -lpthread -luring $*
LD_LIBRARY_PATH=/home/linuxbrew/.linuxbrew/lib ./a.out

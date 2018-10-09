#include <iostream>
#include <time.h>
#include "Matriz.h"

int main() {
    matriz A(10000,10000);
    matriz B(10000,10000);
    matriz C(10000,10000);
    matriz D(10000,10000);

    A.rellenar_matriz();
    B.rellenar_matriz();
    C.rellenar_matriz();

    clock_t tStart = clock();
    D = A*B+C;
    printf("Time taken: %.2fs\n", (double)(clock() - tStart)/CLOCKS_PER_SEC);

}
#include <iostream>
#include <stdio.h>
#include <time.h>
#include "Matriz.h"

int main() {
    matriz A(1000,1000);
    matriz B(1000,1000);
    matriz C(1000,1000);
    matriz D(1000,1000);

    A.rellenar_matriz();
    B.rellenar_matriz();
    C.rellenar_matriz();

    clock_t tStart = clock();
    D = A*B+C;
    printf("Time taken: %.2fs\n", (double)(clock() - tStart)/CLOCKS_PER_SEC);

}

#include <iostream>
#include <stdio.h>
#include <time.h>
#include "Matriz.h"

#define DEFSIZE 1000 // Default size

int main(int argc, char *argv[]) {

    int size;

    // Parse Args
    
    if (argc > 1){
        size = atoi(argv[1]);
    }else{
        size = DEFSIZE;
    }


    // Program

    printf("Starting program with size = %i\n", size);

    matriz A(size,size);
    matriz B(size,size);
    matriz C(size,size);
    matriz D(size,size);

    A.rellenar_matriz();
    A.set_bsize(100);
   // A.pintar_matriz();
   // std::cout << " \n";
    B.rellenar_matriz();
    //B.pintar_matriz();
    C.rellenar_matriz();
    //std::cout << " \n";


    clock_t tStart = clock();
    D = A*B+C;
    //D.pintar_matriz();
    //std::cout << " \n";
    printf("Time taken: %.2fs\n", (double)(clock() - tStart)/CLOCKS_PER_SEC);

}

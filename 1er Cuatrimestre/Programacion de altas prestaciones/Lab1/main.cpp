#include <iostream>
#include "Matriz.h"

int main() {
    matriz m(3,3);
    matriz m2(3,3);
    matriz m3(3,3);

    m.rellenar_matriz();
    m2.rellenar_matriz();


    std::cout << "\n matriz 1\n";
    m.pintar_matriz();

    std::cout << "\n matriz 2\n";
    m2.pintar_matriz();

    //m3 = m + m2;
    //std::cout << "\n matriz resultado\n";
    //m3.pintar_matriz();

    m3 = m*m2;
    std::cout << "\n\n A * B =\n";
    m3.pintar_matriz();

    std::cout << "\n Fila 4 columna 1 del resultado = " << m3(4,1);
    std::cout << "\n Fila 5 columna 5 del resultado = " << m3(5,5);
    std::cout << "\n Fila 2 columna 5 del resultado = " << m3(2,5);
}
//
// Created by Andoni on 26/09/2018.
//

#ifndef LAB1_MATRIZ_H
#define LAB1_MATRIZ_H

class matriz {
public:
    matriz(int filas, int columnas);
    matriz();
    void pintar_matriz();
    void rellenar_matriz();

    double operator ()(double f, double c);
    matriz operator +(matriz &m);
    matriz operator *(matriz &m);
private:
    int filas, columnas;
    double *valores;
};


#endif //LAB1_MATRIZ_H

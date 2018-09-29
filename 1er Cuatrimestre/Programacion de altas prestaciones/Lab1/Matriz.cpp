//
// Created by Andoni on 26/09/2018.
//
#include <iostream>
#include "Matriz.h"

matriz::matriz (int f, int c) :
    filas{f},
    columnas{c}
{
    valores = new double [filas*columnas];
    for(int i = 0; i < (filas*columnas) ; i++){
        valores[i] = 0;
    }

};

matriz::matriz() :
    filas{0},
    columnas{0}
{
}

void matriz::pintar_matriz(){
    using namespace std;
    for(int i = 0; i < (filas*columnas); i++){
     cout << valores[i] << " ";
    if((i+1)%(filas) == 0) cout << "\n";
    }
}

void matriz::rellenar_matriz() {
    double i;
    for (int j = 0;j<(filas*columnas);j++)
    {

        i = rand() % 101;
        valores[j]=i;

    }
}

matriz matriz::operator+(matriz &m) {
    int i, j;
    int fil = m.filas;
    int col = m.columnas;
    matriz matrizRes(fil,col);
    for  (i=0;i<(fil*col);i++)
    {
        matrizRes.valores[i] = this->valores[i]+m.valores[i];
    }
    return matrizRes;
}

double matriz::operator()(double f, double c) {

    int valor = f*(columnas-1) + c-1;
    return valores[valor];

}


# Simón Dice
En este trabajo tuvimos que programar en Android Studio, con Kotlin, una aplicación que simulara un juego del famoso juego de mesa Simón Dice.

## ¿Cómo funciona?
Este trabajo se divide en 4 archivos distintos, el primero, y más importante, es la "MainActivty" donde declararemos cual es la función principal, en este caso se trata de la segunda parte que es la "IU", es decir, la Interfaz de Usuario, en la que podemos encontrar todo lo que aparece en pantalla, es decir, el frontend; dentro de este archivo podemos ver declarado el "MyViewModel" en el cual programamos en sí lo que hará el juego, por lo tanto se le puede denominar como backend, y, por último, está el archivo de "Datos" en el cual almacenamos los distintos estados por los que pasa una partida, como puede ser el inicio, generando la secuencia, entre otros.

## Diagrama de Estados

<img width="758" height="968" alt="Captura de pantalla 2025-11-26 195724" src="https://github.com/user-attachments/assets/bb2129f3-dadd-4a3d-9169-faeee6aeae85" />

## Capturas del programa


En esta primera captura podemos ver como se ve el programa cuando lo abres, tiene los botones en grises y el único que resalta es el botón "Start" para poder empezar el juego.

<img width="560" height="1241" alt="Captura de pantalla 2025-11-26 193829" src="https://github.com/user-attachments/assets/847641e1-2b66-45b8-8d11-c9efc77b767a" />


Una vez presionado "Start" se mostrará la secuencia haciendo que los botones se hagan un poco más grandes, indicando el orden.

<img width="559" height="1248" alt="Captura de pantalla 2025-11-26 193843" src="https://github.com/user-attachments/assets/ac665aae-9430-4aea-9b6c-de2df00ff586" />



Al terminar de mostrar la secuencia, los botones aparecen con color, indicando así que hay que presionar el botón.

<img width="559" height="1246" alt="Captura de pantalla 2025-11-26 193910" src="https://github.com/user-attachments/assets/384ad4ca-1525-4408-a069-254183e359e7" />


Cuando ya se hayan jugado 10 rondas, el juego terminará indicando que has ganado.

<img width="558" height="1245" alt="Captura de pantalla 2025-11-26 193814" src="https://github.com/user-attachments/assets/4ba1913b-c084-4ea3-b37d-6cc642dfd57c" />

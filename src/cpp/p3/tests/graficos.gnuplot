set terminal jpeg size 800,600
set output "complejidad.jpg"
#set xrange [0:200]
set yrange [0:]
set xtics 10
set ytics 1
set xlabel "Cantidad de vértices"
set ylabel ""
set title 'Comprobación de complejidad teórica para grafos aleatorios'
set key top left
set key box
plot 'tiemposPorCantidadDeVertices.txt' using 1:($2 / (log($1)*$1)) title "tiempo / (n log(n))"


set output "complejidadConGrafosCompletos.jpg"
set title 'Comprobación de complejidad teórica para grafos completos'
plot 'tiemposPorCantidadDeVerticesParaGrafosCompletos.txt' using 1:($2 / (log($1)*$1)) title "tiempo / (n log(n))"

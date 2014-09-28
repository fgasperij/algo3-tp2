set terminal jpeg size 800,600
set output "complejidad.jpg"
#set xrange [0:200]
set yrange [0:]
set xtics 10
set ytics 1
set xlabel "Cantidad de vértices"
set ylabel ""
set title 'Comprobación de complejidad teórica'
# log (n * n!) = log(n) + log(n!) = por aprox. Stirling = log(n) + n*log(n) - n
plot 'tiemposPorCantidadDeVertices.txt' using 1:($2 / (log($1)*$1)) with lines linewidth 2 title "tiempo / (n log(n))"

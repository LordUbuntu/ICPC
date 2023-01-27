# Comment out the use bigrat for exact solutions.  Note that
# bigrat will not run all the test data quickly but we can use
# it to check for numerical instability.
#use bigrat ;
$|++ ;
my $verbose = 0 ;
if ($ARGV[0] eq "-v") {
   $verbose++ ;
   shift @ARGV ;
}
my $kases = <> ;
chomp $kases ;
for ($kase=1; $kase<=$kases; $kase++) {
   my $lin = <> ;
   chomp $lin ;
   my $n, $m, $aa, $bb, $c, $d, $r ;
   ($n, $m, $aa, $bb, $c, $d, $r) = map { $_ / 1 } split " ", $lin ;
   $aa /= $r ;
   $bb /= $r ;
   $c /= $r ;
   $d /= $r ;
   my $dep = 0 ;
   my %seen ;
   my $v = $n ;
   while (1) {
      print "v[$dep] = $v\n" if $verbose ;
      $v[$dep] = $v ;
      $dep++ ;
      last if $seen{$v}++ ;
      if ($v <= 0) {
         $v = - $m - 2 * $v ;
      } else {
         $v = $m - 2 * $v ;
      }
   }
   $dep-- ;
   $end = $v[$dep] ;
   my $const = 0 ;
   my $mult = 1 / 1 ;
   for ($i=$dep-1; ; $i--) {
      $v = $v[$i] ;
      print "i $i const $const mult $mult\n" if $verbose ;
      if ($v <= 0) {
         # a + (b + t) f...
         $mm = ($v / $m) * ($v / $m) + $bb ;
 printf("mm %.10g = v %.10g / m %.10g + bb %.10g\n", $mm, $v, $m, $bb) if $verbose ;
         $oconst = $const ;
         $const = $aa + $mm * $const ;
 printf("const %.10g = aa %.10g + mm %.10g * const %.10g\n", $const, $aa, $mm, $oconst) if $verbose ;
         $mult = $mm * $mult ;
      } else {
         # c + (d + t) f...
         $mm = ($v / $m) * ($v / $m) + $d ;
 printf("mm %.10g = v %.10g / m %.10g + d %.10g\n", $mm, $v, $m, $d) if $verbose ;
         $oconst = $const ;
         $const = $c + $mm * $const ;
 printf("const %.10g = c %.10g + mm %.10g * const %.10g\n", $const, $c, $mm, $oconst) if $verbose ;
         $mult = $mm * $mult ;
      }
      last if $v[$i] == $end ;
   }
   #
   #   Now, $v[$end] = $const + $mult * $v[$end]
   #   $v[$end] = $cont / (1 - $mult)
   #
   $f[$dep] = $const / (1/1 - $mult) ;
 printf("Solve f %.10g const %.10g mult %.10g\n", $f[$dep], $const, $mult) if $verbose ;
   for ($i=$dep-1; $i >= 0 ; $i--) {
      $v = $v[$i] ;
      if ($v <= 0) {
         # a + (b + t) f...
         $f = $aa + ($bb + ($v / $m) * ($v / $m)) * $f[$i+1] ;
 printf("f %.10g = aa %.10g + (bb %.10g + v %.10g / m %.10g) * f %.10g\n",
   $f, $aa, $bb, $v, $m, $f[$i+1]) if $verbose ;
      } else {
         # c + (d + t) f...
         $f = $c + ($d + ($v / $m) * ($v / $m)) * $f[$i+1] ;
 printf("f %.10g = c %.10g + (d %.10g + v %.10g / m %.10g) * f %.10g\n",
   $f, $c, $d, $v, $m, $f[$i+1]) if $verbose ;
      }
      $f[$i] = $f ;
      print "At $i v $v f $f\n" if $verbose ;
   }
   printf "%.10g\n", $f[0] ;
}

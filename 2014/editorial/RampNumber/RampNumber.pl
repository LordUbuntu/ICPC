#
#   The faster implementation (this is only one of several different
#   approaches) simply provides a function as follows:
#
#   cnt(start, digs) => number of hill numbers starting with "start"
#   and then with "digs" digits after it.  Every hill number is of the
#   form 1*2*3*4*5*6*7*8*9*; the only question is
#   how many of each digit.  We simply decide what position in the
#   regex we are in based on the leading digits.
#
sub comb {
   my $a = shift ;
   my $b = shift ;
   my $i ;
   return 0 if $b < 0 || $b > $a ;
   my $r = 1 ;
   for ($i=0; $i<$b; $i++) {
      $r = $r * ($a - $i) / ($i + 1) ;
   }
   return $r ;
}
sub cnt {
   my $start = shift ;
   my $digs = shift ;
   my @a = split '', $start ;
   my $at = $a[-1] ;
   my $i = 0 ;
   $i++ while $i+1 < @a && $a[$i] <= $a[$i+1] ;
   return 0 if $i+1 < @a ;
   my $left = 9 - $at ;
   my $r = comb($digs+$left, $left) ;
   return $r ;
}
my $kases = <> ;
chomp $kases ;
for ($kase=1; $kase<=$kases; $kase++) {
   my $v = <> ;
   chomp $v ;
   my @digs = split '', $v ;
   my $i = 0 ;
   $i++ while $i+1 < @digs && $digs[$i] <= $digs[$i+1] ;
   if ($i+1 < @digs) {
      print "-1\n" ;
      next ;
   }
   my $tot = 0 ;
   $tot++ if $v != '0' ;
   for ($i=1; $i<length($v); $i++) {
      $tot += cnt(1, $i) ;
   }
   for ($i=0; $i<length($v); $i++) {
      my $d ;
      for ($d=0; $d<$digs[$i]; $d++) {
         next if $i == 0 && $d == 0 ;
         $tot += cnt(substr($v, 0, $i) . $d, length($v)-$i-1) ;
      }
   }
   print "$tot\n" ;
}

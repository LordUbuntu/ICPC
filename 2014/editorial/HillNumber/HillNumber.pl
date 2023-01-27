#
#   The faster implementation (this is only one of several different
#   approaches) simply provides a function as follows:
#
#   cnt(start, high digs) => number of hill numbers starting with "start"
#   that reaches a high of high and then with "digs" digits after it.
#   Every hill number is of the form 1*2*3*4*5*6*7*8*9*8*7*6*5*4*3*2*1*0*;
#   the only question is how many of each digit.  We simply decide what
#   position in the regex we are in based on the leading digits.
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
   my $high = shift ;
   my $digs = shift ;
   my @a = split '', $start ;
   my $i = 0 ;
   $i++ while $i+1 < @a && $a[$i] <= $a[$i+1] ;
   return 0 if $a[$i] > $high ;
   return 0 if ($digs == 0 || $i+1 < @a) && $a[$i] < $high ;
   my $at ;
   if ($i+1<@a) {
      $at = $a[-1] ;
   } else {
      $at = 2*$high-$a[$i] ;
   }
   my $left = $digs ;
   $left-- if $at > $high ;
   $i++ while $i+1 < @a && $a[$i] >= $a[$i+1] ;
   return 0 if $i+1 < @a ;
   my $r = comb($left+$at, $at) ;
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
   $i++ while $i+1 < @digs && $digs[$i] >= $digs[$i+1] ;
   if ($i+1 < @digs) {
      print "-1\n" ;
      next ;
   }
   my $tot = 1 if $v != '0' ;
   for ($i=1; $i<length($v); $i++) {
      for ($d=1; $d<=9; $d++) {
         for ($h=1; $h<=9; $h++) {
            $tot += cnt($d, $h, $i-1) ;
         }
      }
   }
   for ($i=0; $i<length($v); $i++) {
      my $d ;
      for ($d=0; $d<$digs[$i]; $d++) {
         next if $i == 0 && $d == 0 ;
         for ($h=1; $h<=9; $h++) {
            $tot += cnt(substr($v, 0, $i) . $d, $h, length($v)-$i-1) ;
         }
      }
   }
   print "$tot\n" ;
}

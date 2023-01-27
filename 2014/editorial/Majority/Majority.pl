my $kases = <> ;
chomp $kases ;
for ($kase=1; $kase<=$kases; $kase++) {
   my $cnt = <> ;
   chomp $cnt ;
   @cnt = () ;
   for ($i=0; $i<$cnt; $i++) {
      $cnt[scalar <>]++ ;
   }
   my $best = 1 ;
   for ($i=2; $i<=1000; $i++) {
      $best = $i if $cnt[$i] > $cnt[$best] ;
   }
   print "$best\n" ;
}

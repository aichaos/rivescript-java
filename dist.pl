#!/usr/bin/perl -w

my @files = `find .`;
my @zip   = ();
foreach my $file (@files) {
	chomp($file);
	next if -d $file;
	next if $file =~ /\.svn/;
	next if $file =~ /\.class/;
	$file =~ s/^\.\///;
	push(@zip,$file);
}
my $version = (`grep -e "public static final double VERSION" com/rivescript/RiveScript.java` =~ /([0-9\.]+)/)[0];
print "Java RS Version: $version\n";

print "Tarring...\n";
my $cmd = "tar -czvf rivescript-java-$version.tar.gz @zip";
system($cmd);

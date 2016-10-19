#!/usr/bin/perl -w

# RiveScript Perl 4 Java
# A simple Perl script that allows object macros in RiveScript-Java to run
# Perl code.

use strict;
use warnings;
use RiveScript;
use Getopt::Long;
use JSON;
use File::Basename;
my $self = basename($0);

# Parse CLI options.
my $java = 0; # --java, -j
my $help = 0;
GetOptions (
	'java|j'   => \$java,
	'help|h|?' => \$help,
);

# No valid options?
if (!$java && !$help) {
	print "See $self --help\n";
	exit(1);
}

# If run with --java, the Java code is calling on us for a request.
if ($java) {
	# Parse the incoming params.
	my $json     = JSON->new->utf8->pretty();
	my $incoming = "";
	while (<STDIN>) {
		$incoming .= $_;
	}

	# JSON decode.
	my $data;
	eval {
		$data = $json->decode($incoming);
	};
	if ($@) {
		&error($json, "Invalid JSON data: $@");
	}

	# Verify required keys exist.
	foreach my $key (qw(code vars id message)) {
		if (!exists $data->{$key}) {
			&error($json, "Required JSON key '$key' doesn't exist!");
		}
	}

	# Set up RiveScript.
	my $code = $data->{code};
	my $rs = RiveScript->new();
	$rs->stream(qq{
		+ *
		- <call>handle <star></call>

		> object handle perl
			$code
		< object
	});
	$rs->sortReplies();

	# Set all the user vars.
	foreach my $what (keys %{$data->{vars}}) {
		$rs->setUservar($data->{id}, $what, $data->{vars}->{$what});
	}

	# Get the reply.
	my $reply = $rs->reply($data->{id}, $data->{message});

	# Return the JSON response.
	my $raw = $rs->getUservars($data->{id});
	my $vars = {};
	foreach my $key (keys %{$raw}) {
		next if ref($raw->{$key});
		$vars->{$key} = $raw->{$key};
	}
	my $out = {
		status  => 'ok',
		reply   => $reply,
		vars    => $vars,
	};
	print $json->encode($out);
	exit(0);
}

sub error {
	my ($json, $text) = @_;
	print $json->encode( {
		status  => 'error',
		message => $text,
	} );
	exit(1);
}

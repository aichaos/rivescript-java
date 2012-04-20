// Tests the com.rivescript.lang.Perl handler.

+ encode * in md5
- "<star>" in MD5 is: <call>md5 <star></call>

+ perl version
- Perl RS version: <call>perlver</call>

+ perl set name to *
- Test using Perl handler to set your name. <call>nametest <id> <formal></call>

> object md5 perl
	my ($rs,@args) = @_;
	my $msg = join(" ", @_);

	use Digest::MD5 qw(md5_hex);
	return md5_hex($msg);
< object

> object perlver perl
	my ($rs,@args) = @_;
	return $RiveScript::VERSION;
< object

> object nametest perl
	my $rs = shift;
	my $id = shift;
	my $name = join(" ",@_);

	$rs->setUservar($id, "name", $name);
	return "Done! Name set to $name. Ask 'what is my name' now. ;-)";
< object

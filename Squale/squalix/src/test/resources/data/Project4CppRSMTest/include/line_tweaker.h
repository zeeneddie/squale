#ifndef LINE_TWEAKER
#define LINE_TWEAKER

#include <vector>
#include <string>
#include <sstream>

using std::string;
using std::vector;

typedef std::pair< string, string > class_substitution;
  
string trim( const string& in )
{
  std::stringstream ss;
  string out;
  ss << in;
  ss >> out;
  return out;
}

class line_tweaker
{

public:

  line_tweaker( const string& line ) : _line( line ) { }

  void operator() ( const class_substitution& subst  )
  {
    string::size_type pos( 0 );
    
    while ( pos < _line.length( ) )
      {
	pos= _line.find( subst.first, pos );

	if ( pos < _line.length( ) )
	  {
	    _line= _line.erase( pos, subst.first.length( ) );
	    _line= _line.insert( pos, subst.second );
	    pos+= subst.second.length( );
	    replace_template_params( pos );
	  }
      }
  }

  string::size_type replace_template_params( string::size_type pos )
  {
    string::size_type size;
    vector<string> template_params= parse_template_params( pos, size );
    if ( template_params.size( ) > 0 )
      {
	_line.replace( pos, size, tweak_template_params( template_params ) );
      }
    return ( pos + size );
  }

  virtual string tweak_template_params( const vector<string>& )= 0;

  string tweaked_line( ) const { return _line; }

protected:

  vector<string> parse_template_params( const string::size_type& pos, string::size_type& size ) const
  {
    vector<string> params;
    int template_separator_count( 0 );
    bool start= true;
    string::size_type arg_start( 0 );
    size= 0;

    for ( string::size_type i= pos;
	  i < _line.length( );
	  ++i )
      {
	if ( _line[i] == '<' )
	  {
	    ++template_separator_count;
	  }
	if ( _line[i] == '>' )
	  {
	    --template_separator_count;
	  }
	if ( ( _line[i] == '<' ) and (template_separator_count == 1) )
	  {
	    arg_start= (i + 1);
	  }
	if ( (_line[i] == '>') and (template_separator_count == 0) )
	  {
	    params.push_back( trim( _line.substr( arg_start, i - arg_start ) ) );
	    size = (i - pos + 1);
	    return params;
	  }
	if ( (_line[i] == ',') and (template_separator_count == 1) )
	  {
	    params.push_back( trim( _line.substr( arg_start, i - arg_start ) ) );
	    arg_start= (i + 1); 
	  }
      }
    // cas d'erreur, il n'y a pas de paramètre template,
    // on doit etre dans de la doc ...
    return vector<string>();
  }

protected:

  string _line;
};

#endif

#ifndef HASH_SET_TWEAKER
#define HASH_SET_TWEAKER

#include "line_tweaker.h"

 /* 
  * Les classes suivantes avaient 1 parametre template <T> en 4d
  * Elles ont ont maintenant 4 <T,H,EQ,A> en 12d
  * Il n'y a pas de changement de nom.
  */
class_substitution rw_hash_set_classes[]= { class_substitution( "RWTPtrHashSetIterator", "RWTPtrHashSetIterator" ),
					    class_substitution( "RWTPtrHashSet", "RWTPtrHashSet" ),
					    class_substitution( "RWTValHashSetIterator", "RWTValHashSetIterator" ),
					    class_substitution( "RWTValHashSet", "RWTValHashSet" ) };
					    

class hash_set_tweaker 
  : public line_tweaker
{
public :

  hash_set_tweaker( const string& line ) : line_tweaker( line ) { }
  
  virtual string tweak_template_params( const vector<string>& params )
  {
    string new_params;
    if ( params.size( ) == 1 )
      {
	string key= params.at( 0 );
	string hash= key + "_hash";
	string equal= "std::equal_to<" + key + "> ";

	new_params= 
	  " < " + key + ", "
	  + hash + ", "
	  + equal + " > ";
      }
    return new_params;
  }
};
#endif

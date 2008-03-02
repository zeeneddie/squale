#ifndef SORTED_VECTOR_TWEAKER
#define SORTED_VECTOR_TWEAKER

#include "line_tweaker.h"


 /* 
  * Les classes suivantes avaient 1 parametre template <T> en 4d
  * Elles en ont maintenant 3 <T,C,A> en 12d
  */
class_substitution rw_sorted_vector_classes[]= { class_substitution( "RWTPtrSortedVector", "RWTPtrSortedVector" ),
						 class_substitution( "RWTValSortedVector", "RWTValSortedVector" ) };


class sorted_vector_tweaker 
  : public line_tweaker
{
public :

  sorted_vector_tweaker( const string& line ) : line_tweaker( line ) { }
  
  virtual string tweak_template_params( const vector<string>& params )
  {
    string new_params;
    if ( params.size( ) == 1 )
      {
	string key= params.at( 0 );
	string less= "std::less<" + key + "> ";

	new_params= 
	  " < " + key + ", "
	  + less + " > ";
      }
    return new_params;
  }
};

#endif

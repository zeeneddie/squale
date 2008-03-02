#ifndef HASH_DICT_TWEAKER
#define HASH_DICT_TWEAKER

#include "line_tweaker.h"


 /* 
  * Les classes suivantes avaient 2 parametres template <K,V> en 4d
  * Elles ont ont maintenant 4 <K,T,H,EQ> en 12d
  *
  */
class_substitution rw_hash_dict_classes[]= { class_substitution( "RWTPtrHashDictionaryIterator", "RWTPtrHashMapIterator" ),
					     class_substitution( "RWTPtrHashDictionary", "RWTPtrHashMap" ),
					     class_substitution( "RWTValHashDictionaryIterator", "RWTValHashMapIterator" ),
					     class_substitution( "RWTValHashDictionary", "RWTValHashMap" ) };
					     


class hash_dict_tweaker 
  : public line_tweaker
{
public :

  hash_dict_tweaker( const string& line ) : line_tweaker( line ) { }
  
  virtual string tweak_template_params( const vector<string>& params )
  {
    string new_params;
    if ( params.size( ) == 2 )
      {
	string key= params.at( 0 );
	string type= params.at( 1 );
	string hash= key + "_hash";
	string equal= "std::equal_to<" + key + "> ";

	new_params= 
	  " < " + key + ", "
	  + type + ", "
	  + hash + ", "
	  + equal + " > ";
      }
    return new_params;
  }
};

#endif

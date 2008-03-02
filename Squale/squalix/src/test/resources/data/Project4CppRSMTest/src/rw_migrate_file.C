#include <fstream>
#include <string>

#include "hash_set_tweaker.h"
#include "hash_dict_tweaker.h"
#include "sorted_vector_tweaker.h"

using std::string;

extern class_substitution rw_hash_set_classes[];
extern class_substitution rw_hash_dict_classes[];
extern class_substitution rw_sorted_vector_classes[];

 /*
  * la longueur maximum d'une ligne de code
  */
std::streamsize max_line_size= 16*1024;



void rw_migrate_file( std::ifstream& input, std::ofstream& output )
{
  char *buffer= new char[ max_line_size ];

  while ( input.getline( buffer, max_line_size ) )
  {
    string line( buffer );

    size_t rw_hash_dict_classes_size=     sizeof( rw_hash_dict_classes )     / sizeof( class_substitution );
    size_t rw_hash_set_classes_size=      sizeof( rw_hash_set_classes )      / sizeof( class_substitution );
    size_t rw_sorted_vector_classes_size= sizeof( rw_sorted_vector_classes ) / sizeof( class_substitution );

    hash_dict_tweaker tweak_hash_dict( line );

    tweak_hash_dict= for_each( &rw_hash_dict_classes[0],
			       &rw_hash_dict_classes[rw_hash_dict_classes_size],
			       tweak_hash_dict );

    hash_set_tweaker tweak_hash_set( tweak_hash_dict.tweaked_line( ) );

    for_each( &rw_hash_set_classes[0],
	      &rw_hash_set_classes[rw_hash_set_classes_size],
	      tweak_hash_set );

    sorted_vector_tweaker tweak_sorted_vector( tweak_hash_set.tweaked_line( ) );

    for_each( &rw_sorted_vector_classes[0],
	      &rw_sorted_vector_classes[rw_sorted_vector_classes_size],
	      tweak_sorted_vector );
  
    output << tweak_sorted_vector.tweaked_line( ) << std::endl;

  }
  delete buffer;
}

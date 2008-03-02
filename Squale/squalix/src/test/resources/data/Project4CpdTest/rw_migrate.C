#include <string>
#include <fstream>



using std::string;
using std::ifstream;
using std::ofstream;


class CG {
  virtual void process();
};

void rw_migrate_file( ifstream&, ofstream& );



int rw_main(int argc, char* argv[] )
{
  if (argc == 2)
    {
      string ifilename( argv[1] );
      string ofilename( ifilename + "_rw" );

      ifstream  input( ifilename.c_str( ) );
      ofstream output( ofilename.c_str( ) );

      if ( !input.is_open( ) )
	{
	  std::cerr << "Can't open input file " << ifilename << std::endl;
	  return 1;
	}
      if ( !output.is_open( ) )
	{
	  std::cerr << "Can't open output file " << ofilename << std::endl;
	  return 2;
	}

      rw_migrate_file( input, output );
    }
  else
    {
      std::cout << "Usage : rw_standard inputfile.C" << std::endl;
    }
  return 0;
}

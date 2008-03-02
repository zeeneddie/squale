#include <string>

// specifications
class Printer {
	private : 
		// the string to print
		std::string mString;
	public :
		Printer() {};
		void setString(std::string lString);
		std::string getString() const ;
};


// implementations
std::string Printer::getString() const {
	return mString;
}

void Printer::setString(std::string lString) {
	mString = lString;
}

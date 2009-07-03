#!/bin/sh

INSTALL_DIR=$1

# make the sh files executable
chmod u+x $INSTALL_DIR/*.sh
chmod u+x $INSTALL_DIR/database/*.sh
chmod u+x $INSTALL_DIR/server/bin/*.sh
chmod u+x $INSTALL_DIR/squale.home/Squalix/bin/*.sh
chmod u+x $INSTALL_DIR/squale.home/Squalix/bin/resources/*.sh

# remove windows file
rm $INSTALL_DIR/*.bat
rm $INSTALL_DIR/database/*.bat
rm $INSTALL_DIR/server/bin/*.bat
rm $INSTALL_DIR/squale.home/Squalix/bin/*.bat
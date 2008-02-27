find $1 -name "*.jsp" | wc -l > $2;
find $1 -name "*.jsp" -exec cat {} \; | grep -vx [[:space:]]* | wc -l >> $2;

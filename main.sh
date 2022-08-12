clear
cd Chess

if [ -d "bin/me/abhaydaksh/chess" ]; then
	java -cp bin me.abhaydaksh.chess.Main
	exit
fi

printf "Compiling..."
javac -d bin src/me/abhaydaksh/chess/*.java src/me/abhaydaksh/chess/pieces/*.java
if [ $? == 0 ] 
then
    clear
    java -cp bin me.abhaydaksh.chess.Main
else
    printf "\n\n\nError - Exit code $?"
fi
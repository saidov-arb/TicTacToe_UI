public final class TTTPlayer extends Player
{
    private char symbol;

    public char getSymbol() { return this.symbol; }
    public void setSymbol(char symbol) { this.symbol = symbol; }

    TTTPlayer(String name,char symbol)
    {
        super(name);
        setSymbol(symbol);
    }
}

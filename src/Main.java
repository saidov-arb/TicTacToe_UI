import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class Main extends Application {
    public static void main(String[] args) {
        /**
         * @Author: Saidov Arbi
         * @Date: 15.06.2020
         * Beschreibung: TicTacToe mit User Interface.
         **/
        launch(args);
    }


    GridPane layoutInfo,layoutGame;
    Label[] schriften;
    TextField[] nameInput;
    Button[][] gameButtons;
    Button[] usefulButtons,symbolPick;
    Font header = new Font(30),texts = new Font(22);
    final String bTextP1 = "Enter Name P1.",bTextP2 = "Enter Name P2.",bTextRe = "RESTART.";
    int standartWidth = 120, standartHeight = 30, buttonSize = 120, symbolPickWidth = 120, symbolPickHeight = 40,playerSwitcher = 0;
    TTTPlayer[] tttPlayers = new TTTPlayer[2];
    Game game;



    public void start(Stage primaryStage) throws Exception
    {
        //tttPlayers definieren.
        tttPlayers[0] = new TTTPlayer("", ' ');
        tttPlayers[1] = new TTTPlayer("", ' ');


        //Labels bauen.
        schriften = new Label[6];
        buildLabels();


        //TextFields bauen.
        nameInput = new TextField[2];
        buildTextFields();


        //gameButtons bauen.
        gameButtons = new Button[Game.FIELD_SIZE][Game.FIELD_SIZE];
        buildGameButtons();


        //UsefulButtons bauen.
        usefulButtons = new Button[3];
        buildUsefulButtons();


        //SymbolPick bauen.
        symbolPick = new Button[9];
        buildSymbolPick();




        //Layouts bauen.
        layoutGame = new GridPane();
        buildLayoutGame();
        layoutInfo = new GridPane();
        buildLayoutInfo();



        //Stage und Scene bauen und Stage starten.
        Scene fullScene = new Scene(layoutInfo,440,650);
        primaryStage.setTitle("TicTacToe_UI (Gebaut von Saidov)");
        primaryStage.setScene(fullScene);
        primaryStage.show();
    }



    //Hier baue ich die Funktionalität für die Tasten zur Festlegung der beiden Namen und zum zurücksetzen des Spiels.
    EventHandler<MouseEvent> usefulButtonsFunktion = new EventHandler<MouseEvent>()
    {
        public void handle(MouseEvent mouseEvent)
        {
            Button cacheB = (Button) mouseEvent.getSource();
            Label name = new Label();
            name.setFont(new Font(22));

            switch (cacheB.getText())
            {
                case bTextP1:
                    if (nameInput[0].getText().equals(""))
                    {
                        tttPlayers[0].setName("PlayerONE");
                    }
                    else
                    {
                        tttPlayers[0].setName(nameInput[0].getText());
                    }
                    name.setText(tttPlayers[0].getName());
                    layoutInfo.getChildren().remove(nameInput[0]);
                    layoutInfo.getChildren().remove(usefulButtons[0]);
                    layoutInfo.add(name,1,1);
                    break;
                case bTextP2:
                    if (nameInput[1].getText().equals(""))
                    {
                        tttPlayers[1].setName("PlayerTWO");
                    }
                    else
                    {
                        tttPlayers[1].setName(nameInput[1].getText());
                    }
                    name.setText(tttPlayers[1].getName());
                    layoutInfo.getChildren().remove(nameInput[1]);
                    layoutInfo.getChildren().remove(usefulButtons[1]);
                    layoutInfo.add(name,1,2);
                    break;
                case bTextRe:
                    try
                    {
                        game.restartGame();
                        playerSwitcher = 0;
                        schriften[5].setText(game.getTTTPlayer()[0].getName()+" setze.");
                        for (Button[] gameButton : gameButtons)
                        {
                            for (int j = 0; j < gameButtons.length; j++)
                            {
                                gameButton[j].setText(" ");
                                gameButton[j].addEventFilter(MouseEvent.MOUSE_CLICKED,gameButtonsFunktion);
                            }
                        }
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println("Game nicht definiert. Somit kann es auch nicht zurückgesetzt werden.");
                        if (tttPlayers[0].getName().equals("")||tttPlayers[1].getName().equals(""))
                        {
                            schriften[5].setText("Namen?");
                        }
                        else if (tttPlayers[0].getSymbol() == ' '||tttPlayers[1].getSymbol() == ' ')
                        {
                            schriften[5].setText("Symbole?");
                        }
                    }
                    break;
            }
            if(game == null)
            {
                createGameIfPossible();
            }
        }
    };

    //Hier kann man die Symbole dann auswählen.
    EventHandler<MouseEvent> symbolPickFunktion = new EventHandler<MouseEvent>()
    {
        public void handle(MouseEvent mouseEvent)
        {
            Button cacheB = (Button) mouseEvent.getSource();
            Label symbolLabel = new Label(cacheB.getText());
            symbolLabel.setFont(texts);
            char sPick = getSymbolFromButton(cacheB);
            if (playerSwitcher == 0)
            {
                removeSymbolPick(cacheB.getText());
                tttPlayers[0].setSymbol(sPick);
                layoutInfo.add(symbolLabel,2,playerSwitcher+1,1,1);
                schriften[5].setText("Player 2 Zeichen ↓");
                playerSwitcher++;
            }
            else if (playerSwitcher == 1)
            {
                removeSymbolPick();
                tttPlayers[1].setSymbol(sPick);
                layoutInfo.add(symbolLabel,2,playerSwitcher+1,1,1);
                playerSwitcher = 0;
                schriften[5].setText("");
            }

            createGameIfPossible();
        }
    };

    //Hier werden die gameButtons funktionsfähig. WARNUNG!!! Epileptische Anfälle, auf Grund tausendfacher Verzweigung, könnten durchaus auftreten!!! ACHTUNG!!!
    EventHandler<MouseEvent> gameButtonsFunktion = new EventHandler<MouseEvent>()
    {
        public void handle(MouseEvent mouseEvent)
        {
            Button cacheB = (Button) mouseEvent.getSource();
            for (int i = 0; i < Game.FIELD_SIZE; i++)
            {
                for (int j = 0; j < Game.FIELD_SIZE; j++)
                {
                    if (gameButtons[i][j] == cacheB)
                    {
                        if (game.getBoard().checkForSpace(i,j))
                        {
                            game.getBoard().setSymbol(playerSwitcher + 1, i, j);
                            gameButtons[i][j].setText(String.valueOf(game.getTTTPlayer()[playerSwitcher].getSymbol()));
                            switch (game.checkForWinner())
                            {
                                case 1: schriften[5].setText(game.getTTTPlayer()[0].getName()+" hat gewonnen!");
                                break;
                                case 2: schriften[5].setText(game.getTTTPlayer()[1].getName()+" hat gewonnen!");
                                break;
                            }
                            if (game.checkForWinner() != 0)
                            {
                                for (int k = 0; k < Game.FIELD_SIZE; k++)
                                {
                                    for (int l = 0; l < Game.FIELD_SIZE; l++)
                                    {
                                        gameButtons[k][l].removeEventFilter(MouseEvent.MOUSE_CLICKED,gameButtonsFunktion);
                                    }
                                }
                            }
                            else if (game.checkIfFieldIsFull())
                            {
                                schriften[5].setText("UNENTSCHIEDEN!");
                            }
                            playerSwitcher++;
                            if (playerSwitcher == 2)
                            {
                                playerSwitcher = 0;
                            }
                            if (game.checkForWinner() == 0&&!game.checkIfFieldIsFull())
                            {
                                schriften[5].setText(game.getTTTPlayer()[playerSwitcher].getName() + " setze.");
                            }
                        }
                    }
                }
            }
        }
    };




    //--------------------- Folgende Methoden wurden hauptsächlich zur Unterstützung der Lesbarkeit gebaut. ---------------------

    //Nach Auswahl von Symbol, soll entweder das Ausgewählte, oder alle Symbole entfernt werden.
    void removeSymbolPick()
    {
        for (Button button : symbolPick)
        {
            layoutInfo.getChildren().remove(button);
        }
    }
    void removeSymbolPick(String cachePick)
    {
        for (Button button : symbolPick)
        {
            if (button.getText().equals(cachePick))
            {
                layoutInfo.getChildren().remove(button);
            }
        }
    }

    //Wenn Namen und Symbole, beide existieren, soll ein Game erstellt werden, mit beiden Playern.
    void createGameIfPossible()
    {
        if ((tttPlayers[0].getSymbol() != ' ')&&(tttPlayers[1].getSymbol() != ' ')&&(!tttPlayers[0].getName().equals(""))&&(!tttPlayers[1].getName().equals("")))
        {
            layoutInfo.add(layoutGame,0,4,3,3);
            layoutInfo.add(usefulButtons[2],2,8);
            game = new Game(tttPlayers[0],tttPlayers[1]);
            schriften[5].setText(game.getTTTPlayer()[playerSwitcher].getName()+" setze.");
        }
    }

    //Baut "schriften".
    void buildLabels()
    {
        //--- Label definieren.
        schriften[0] = new Label("Heftigstes TicTacToe!");
        schriften[1] = new Label("Player 1: ");
        schriften[2] = new Label("Player 2: ");
        schriften[3] = new Label(" ");
        schriften[4] = new Label(" ");
        schriften[5] = new Label("Player 1 Zeichen ↓");

        //--- Label größen und fonts festlegen.
        schriften[0].setPrefSize(360, 50);
        schriften[0].setFont(header);
        for (int i = 1; i < schriften.length - 1; i++)
        {
            schriften[i].setPrefSize(standartWidth, standartHeight);
            schriften[i].setFont(texts);
        }
        schriften[5].setPrefSize(standartWidth * 2, standartHeight);
        schriften[5].setFont(new Font(texts.getSize()*0.8));
    }

    //Baut "nameInput".
    void buildTextFields()
    {
        //--- TextFields definieren.
        for (int i = 0; i < nameInput.length; i++)
        {
            nameInput[i] = new TextField("");
        }

        //--- TextFields größe festlegen.
        for (TextField textField : nameInput)
        {
            textField.setPrefSize(standartWidth, standartHeight);
        }
    }

    //Baut "gameButtons".
    void buildGameButtons()
    {
        //--- Definiere gameButtons.
        for (int i = 0; i < Game.FIELD_SIZE; i++)
        {
            for (int j = 0; j < Game.FIELD_SIZE; j++)
            {
                gameButtons[i][j] = new Button(" ");
            }
        }

        //--- gameButtons Größen und Farben festlegen. "gameButtonsFunktion" Handler zuweisen.
        for (int i = 0; i < Game.FIELD_SIZE; i++)
        {
            for (int j = 0; j < Game.FIELD_SIZE; j++)
            {
                gameButtons[i][j].setPrefSize(buttonSize,buttonSize);
                gameButtons[i][j].setStyle("-fx-base: black;");
                gameButtons[i][j].setFont(new Font(texts.getSize()*2.2));
                gameButtons[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED,gameButtonsFunktion);
            }
        }
    }

    //Baut "usefulButtons".
    void buildUsefulButtons()
    {
        //--- Definiere usefulButtons.
        usefulButtons[0] = new Button(bTextP1);
        usefulButtons[1] = new Button(bTextP2);
        usefulButtons[2] = new Button(bTextRe);

        //--- UsefulButtons Größen und Farben festlegen. "usefulButtonsFunktion" Handler zuweisen.
        for (Button usefulButton : usefulButtons)
        {
            usefulButton.setPrefSize(standartWidth, standartHeight);
            usefulButton.setStyle("-fx-base: rgb(0,0,0)");
            usefulButton.addEventFilter(MouseEvent.MOUSE_CLICKED, usefulButtonsFunktion);
        }
    }

    //Baut "symbolPick".
    void buildSymbolPick()
    {
        //--- Definiere symbolPick.
        symbolPick[0] = new Button("  ?");
        symbolPick[1] = new Button("  !");
        symbolPick[2] = new Button("  ☺");
        symbolPick[3] = new Button("  ☻");
        symbolPick[4] = new Button("  ♥");
        symbolPick[5] = new Button("  ♦");
        symbolPick[6] = new Button("  ♣");
        symbolPick[7] = new Button("  ♠");
        symbolPick[8] = new Button("  #");

        //--- SymbolPick Größen, Farben und Funktion festlegen.
        for (Button button : symbolPick)
        {
            button.setPrefSize(symbolPickWidth, symbolPickHeight);
            button.setStyle("-fx-base: rgb(0,0,0)");
            button.setFont(texts);
            button.addEventFilter(MouseEvent.MOUSE_CLICKED, symbolPickFunktion);
        }
    }

    //Vom geclicktem "symbolPick", sollen die vorher eingetragenen Leerzeichen entfernt, und das Symbol zurückgegeben werden.
    char getSymbolFromButton(Button cacheB)
    {
        char[] cacheBtextArray = cacheB.getText().toCharArray();
        for (char c : cacheBtextArray)
        {
            if (c != ' ')
            {
                return c;
            }
        }
        return ' ';
    }

    //Baut "layoutInfo", mit "layoutGame" drinnen. Unmöglich, wenn "layoutGame" nicht definiert wurde.
    void buildLayoutInfo()
    {
        //--- LayoutInfo kriegt schriften.
        layoutInfo.add(schriften[0],0,0,3,1);
        for (int i = 1; i < schriften.length-2; i++)
        {
            layoutInfo.add(schriften[i],0,i,1,1);
        }
        layoutInfo.add(schriften[4],0,7);
        layoutInfo.add(schriften[5],0,8,2,1);

        //--- LayoutInfo kriegt nameInput.
        for (int i = 0; i < nameInput.length; i++)
        {
            layoutInfo.add(nameInput[i],1,i+1);
        }

        //--- LayoutInfo kriegt usefulButtons.
        for (int i = 0; i < usefulButtons.length-1; i++)
        {
            layoutInfo.add(usefulButtons[i],2,i+1);
        }

        //--- LayoutInfo kriegt smybolPick.
        byte a = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                layoutInfo.add(symbolPick[a], j, i+9);
                a++;
            }
        }

        //--- LayoutInfo wird zentriert und bekommt eine Hintergrundfarbe.
        layoutInfo.alignmentProperty().set(Pos.CENTER);
        layoutInfo.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    //Baut "layoutGame", mit den gameButtons.
    void buildLayoutGame()
    {
        //--- LayoutGame kriegt Buttons.
        layoutGame.setGridLinesVisible(true);
        for (int i = 0; i < Game.FIELD_SIZE; i++)
        {
            for (int j = 0; j < Game.FIELD_SIZE; j++)
            {
                layoutGame.add(gameButtons[i][j],j,i);
            }
        }
    }
}

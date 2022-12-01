package me.hdcookie.games.TruthOrDare;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class TODListener extends ListenerAdapter {
    private Database database;
    private TODManager todManager;


    public TODListener(Database database, TODManager todManager) {
        this.database = database;
        this.todManager = todManager;

    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        try {


            if (event.getComponentId().contains("truth")) {
                //Separate the game id from the button id
                String[] split = event.getComponentId().split("_");
                String id = split[1];

                database.setTruthOrDare(id, "truth");
                event.reply("collected your answer, you will be pinged in tod channel when the asker has asked the question").queue();                         

                todManager.dmAsker(event.getJDA().getGuildById(id), database);



            } else if (event.getComponentId().contains("dare")) {
                //seperate the id from the button id
                String[] split = event.getComponentId().split("_");
                String id = split[1];

                todManager.dmAsker(event.getJDA().getGuildById(id), database);

                database.setTruthOrDare(id, "dare");
                event.reply("collected your answer, you will be pinged in tod channel when the asker has asked the question").queue();
            }else if(event.getComponentId().contains("Answer")){
                String[] split = event.getComponentId().split("_");
                String id = split[1];

                //send modal
                String truthOrDare = database.getTruthOrDare(id);
                event.replyModal(todManager.getApplyModal(truthOrDare, event.getJDA().getGuildById(id))).queue();
            }else if(event.getComponentId().contains("new_game")){
                String[] split = event.getComponentId().split("_");
                String id = split[2];

                TruthOrDare truthOrDare = new TruthOrDare(todManager, database);
                truthOrDare.newGame(event.getJDA().getGuildById(id));

            }



        } catch (SQLException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event){
        if(event.getModalId().contains("question")){
            String[] split = event.getModalId().split("_");
            String id = split[1];

            String answer = event.getValue("question").getAsString();

            try {
                database.setQuestion(id, answer);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //finishGame
            try {
                TruthOrDare truthOrDare = new TruthOrDare(todManager, database);
                truthOrDare.finishGame(event.getJDA().getGuildById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            event.reply("Answer submitted").queue();
        }
    }


}

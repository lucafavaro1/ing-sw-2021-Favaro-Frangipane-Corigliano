package it.polimi.ingsw.server.model.Leader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.server.model.BadFormatException;
import it.polimi.ingsw.server.model.DeckOfCards;
import it.polimi.ingsw.server.model.RequirementsAndProductions.CardRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.SerializationType;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Class that describes a deck made of leader cards
 */
public class LeaderCardDeck extends DeckOfCards<LeaderCard> {
    /**
     * Constructor that loads the deck from a JSON file in the repo
     */
    public LeaderCardDeck() throws FileNotFoundException {
        super("/Server/leaderCards.json");
    }

    @Override
    public LeaderCard parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        JsonObject cardObj = jsonCard.getAsJsonObject();
        JsonObject cardAbility = cardObj.get("cardAbility").getAsJsonObject();

        // deserializing the ability in base of the abilityType
        LeaderAbility ability;
        Abil_Enum typeAbility = gson.fromJson(cardAbility.get("abilityType"), Abil_Enum.class);

        ability = (LeaderAbility) gson.fromJson(cardAbility, typeAbility.getEventClass());
        if(typeAbility == Abil_Enum.PRODUCTION)
            ((MoreProduction) ability).getProduction().setSerializationType(SerializationType.PRODUCTION);

        ability.setSerializationType(SerializationType.LEADER_ABILITY);

        // deserializing the card requirements
        CardRequirements cardRequirements = Optional.ofNullable(gson.fromJson(cardObj.get("cardRequirements"), CardRequirements.class))
                .orElse(new CardRequirements(List.of()));
        cardRequirements.setSerializationType(SerializationType.CARD_REQUIREMENTS);

        // deserializing the resource requirements
        ResRequirements resRequirements = Optional.ofNullable(gson.fromJson(cardObj.get("resRequirements"), ResRequirements.class))
                .orElse(new ResRequirements(List.of()));
        resRequirements.setSerializationType(SerializationType.RES_REQUIREMENTS);

        // Creating the Leader Card
        LeaderCard leaderCard = new LeaderCard(
                ability,
                cardRequirements,
                resRequirements,
                gson.fromJson(cardObj.get("cardVictoryPoints"), Integer.class)
        );

        if (leaderCard.isAllowed())
            return leaderCard;
        else
            throw new BadFormatException();
    }
}

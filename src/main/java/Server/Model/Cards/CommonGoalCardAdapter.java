package Server.Model.Cards;

import Server.Model.Cards.CommonGoalCards.*;
import Server.Model.GameItems.PointsTile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;


public class CommonGoalCardAdapter extends TypeAdapter<CommonGoalCard>{
    @Override
    public void write(JsonWriter out, CommonGoalCard abstractAttribute) throws IOException {
        if (abstractAttribute instanceof CommonGoalCard1) {
            out.beginObject();
            out.name("type").value("CommonGoalCard1");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard2) {
            out.beginObject();
            out.name("type").value("CommonGoalCard2");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard3) {
            out.beginObject();
            out.name("type").value("CommonGoalCard3");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard4) {
            out.beginObject();
            out.name("type").value("CommonGoalCard4");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard5) {
            out.beginObject();
            out.name("type").value("CommonGoalCard5");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard6) {
            out.beginObject();
            out.name("type").value("CommonGoalCard6");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard7) {
            out.beginObject();
            out.name("type").value("CommonGoalCard7");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard8) {
            out.beginObject();
            out.name("type").value("CommonGoalCard8");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard9) {
            out.beginObject();
            out.name("type").value("CommonGoalCard9");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard10) {
            out.beginObject();
            out.name("type").value("CommonGoalCard10");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard11) {
            out.beginObject();
            out.name("type").value("CommonGoalCard11");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        } else if (abstractAttribute instanceof CommonGoalCard12) {
            out.beginObject();
            out.name("type").value("CommonGoalCard12");
            out.name("pointsTiles");
            out.beginArray();
            for (PointsTile pointsTile : abstractAttribute.getPointsTiles()) {
                out.value(pointsTile.name());
            }
            out.endArray();
            out.endObject();
        }


    }

    @Override
    public CommonGoalCard read(JsonReader in) throws IOException {
        String type = null;
        List<PointsTile> pointsTiles = new ArrayList<>();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("type")) {
                type = in.nextString();
            } else if (name.equals("pointsTiles")) {
                in.beginArray();
                while (in.hasNext()) {
                    pointsTiles.add(PointsTile.valueOf(in.nextString()));
                }
                in.endArray();
            }
        }
        in.endObject();

        CommonGoalCard commonGoalCard = null;
        if (type != null) {
            if (type.equals("CommonGoalCard1")) {
                commonGoalCard = new CommonGoalCard1();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard2")){
                commonGoalCard = new CommonGoalCard2();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard3")){
                commonGoalCard = new CommonGoalCard3();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard4")){
                commonGoalCard = new CommonGoalCard4();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard5")){
                commonGoalCard = new CommonGoalCard5();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard6")){
                commonGoalCard = new CommonGoalCard6();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard7")){
                commonGoalCard = new CommonGoalCard7();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard8")){
                commonGoalCard = new CommonGoalCard8();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard9")){
                commonGoalCard = new CommonGoalCard9();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard10")){
                commonGoalCard = new CommonGoalCard10();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard11")){
                commonGoalCard = new CommonGoalCard11();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else if(type.equals("CommonGoalCard12")){
                commonGoalCard = new CommonGoalCard12();
                commonGoalCard.setPointsTiles(pointsTiles);
            }else{
                throw new IOException("Unknown type: " + type);
            }
        }

        return commonGoalCard;
    }
}


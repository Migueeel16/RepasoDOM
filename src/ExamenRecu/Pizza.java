package ExamenRecu;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    String nom;
    int preu;
    List<String> ingredients = new ArrayList<>();

    public Pizza(List<String> ingredients, String nom, int preu) {
        this.ingredients = ingredients;
        this.nom = nom;
        this.preu = preu;
    }

    public Pizza() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "ingredients=" + ingredients.toString() +
                ", nom='" + nom + '\'' +
                ", preu=" + preu +
                '}' + "\n";
    }
}

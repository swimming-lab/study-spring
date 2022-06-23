package swm.spring.springdi.dip;

public class ItalianBMT {
    // DIP 적용 전
    private MealBread mealBread;
    private ParmesanCheese parmesanCheese;

    public ItalianBMT(MealBread mealBread, ParmesanCheese parmesanCheese) {
        this.mealBread = mealBread;
        this.parmesanCheese = parmesanCheese;
    }


    // DIP 적용 후
    private Bread bread;
    private Cheese cheese;

    public ItalianBMT(Bread bread, Cheese cheese) {
        this.bread = bread;
        this.cheese = cheese;
    }
}
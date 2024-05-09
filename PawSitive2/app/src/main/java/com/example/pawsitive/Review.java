package com.example.pawsitive;
public class Review
{
    String reviewString;
    int noOfStar;

    public Review(int noOfStar, String reviewString)
    {
        this.noOfStar = noOfStar;
        this.reviewString = reviewString;
    }

    public int getNoOfStar() {
        return noOfStar;
    }

    public String getReviewString() {
        return reviewString;
    }

    public static double calculateStarAverage(User user)
    {
        if(user.getReviews().size() == 0)
        {
            return 0;
        }
        double sumOfStars = 0;
        for(int i = 0; i < user.getReviews().size(); i++)
        {
            sumOfStars+= user.getReviews().get(i).getNoOfStar();
        }
        double sumOfStarsTimesTwo = 2 * sumOfStars;
        double averageStarsTimesTwo = sumOfStarsTimesTwo / user.getReviews().size();
        double averageStarsTimesTwoRounded = Math.round(averageStarsTimesTwo);
        return  averageStarsTimesTwoRounded/2;
    }

    public static int getResource(double averageStar)
    {
        if(averageStar == 0.5)
        {
            return (R.drawable.halfstar);
        }
        if(averageStar == 1.5)
        {
            return (R.drawable.oneandhalfstar);
        }
        if(averageStar == 2.5)
        {
            return (R.drawable.twoandhalfstar);
        }
        if(averageStar == 3.5)
        {
            return (R.drawable.threeandhalfstar);
        }
        if(averageStar == 4.5)
        {
            return (R.drawable.fourandhalfstar);
        }
        if(averageStar == 0)
        {
            return (R.drawable.zerostar);
        }
        if(averageStar == 1)
        {
            return (R.drawable.onestar);
        }
        if(averageStar == 2)
        {
            return (R.drawable.twostar);
        }
        if(averageStar == 3)
        {
            return (R.drawable.threestar);
        }
        if(averageStar == 4)
        {
            return (R.drawable.fourstar);
        }
        if(averageStar == 5)
        {
            return (R.drawable.fivestar);
        }
        throw new RuntimeException("average can be an intager from 0 to 5 or an (intager + 0.5) < 5");
    }

}
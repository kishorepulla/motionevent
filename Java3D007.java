/* File Java3D007.java
Copyright 2007, R.G.Baldwin

The purpose of this program is to investigate and
illustrate the behavior of the Java 3D class named Alpha
as a function of the values passed as parameters when
an Alpha object is instantiated.

The class named Alpha is a programmable time-base program
that is used mainly to control animations, but could be
used for any purpose that requires a programmable time
base.

The object generates and provides an alpha value ranging
from 0.0 to 1.0 over a specified elapsed cycle time.  The
shape of the time function described by the alpha
value is controlled by parameters that are passed to the
constructor when the object is instantiated.  The number
of repetitive cycles of the alpha value that will be
produced is an input parameter.  A value of -1 will cause
the object to continue producing repetitive alpha values
until it is purposely terminated.

The object has a method named value(), which can be used
to sample the alpha value at any point in time.
By using a clock and sampling the alpha value at a set of
equally spaced points in time, a set of sample values can
be obtained that describe the alpha time function.

The Alpha class contains four overloaded constructors. The
most complex constructor requires ten parameters.  The
only differences among the constructors is that some of
the constructors default some of the ten required
parameters, resulting in constructors having different
numbers of parameters.

This program constructs six different Alpha objects, each
having different parameter values.  All six constructors
run for two cycles with a duration of five seconds per
cycle.  Thus, each Alpha object generates alpha values
during a time span of ten seconds (10,000 milliseconds)

A timer is used to collect 100 samples from each Alpha
object spaced at uniform intervals of 100 milliseconds
during the total elapsed time of ten seconds.

As a quality check, the system clock is used to get and
print the total elapsed time during which the Alpha
objects are generating data.

Each set of 100 samples is plotted in a format that
displays the six plots in a vertical stack, one plot above
the other.  The plot data is scaled so that a value of 1.0
produced by an Alpha object is plotted as a value of 100 
on the plot.

The plotting software is a simplified version of the
program named Graph01 that was published earlier in a
tutorial lesson number 1468 titled  "Plotting Engineering
and Scientific Data using Java."


Tested using Java SE 6, and Java 3D 1.5.0 running under
Windows XP.
*********************************************************/
import javax.media.j3d.Alpha;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Frame;
import java.util.Date;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;

public class Java3D007{

  Alpha alpha1Obj;
  Alpha alpha2Obj;
  Alpha alpha3Obj;
  Alpha alpha4Obj;
  Alpha alpha5Obj;
  Alpha alpha6Obj;

  float[] alpha1Data = new float[100];
  float[] alpha2Data = new float[100];
  float[] alpha3Data = new float[100];
  float[] alpha4Data = new float[100];
  float[] alpha5Data = new float[100];
  float[] alpha6Data = new float[100];

  //Initialize the sampleTime counter to the time that the
  // first sample will be taken.
  int sampleTime = 100;
  //Used to compute the actual elapsed time from the
  // system clock.
  long baseTime;
  //Timer used to trigger the collection of samples of the
  // alpha values.
  Timer samplingTimer;

  public static void main(String[] args){
    new Java3D007();
  }//end main
  //----------------------------------------------------//

  Java3D007(){//constructor

    //Create a timer that will fire once each 100
    // milliseconds.  Get and save the current alpha value
    // from each Alpha object each time the timer fires, 
    samplingTimer = new Timer(100,
      new ActionListener(){
        public void actionPerformed(ActionEvent e){



          if(sampleTime >= 10000){
            //Time to quit taking samples.  Get and
            // display the actual elapsed time from the
            // system clock.
            System.out.println("Elapsed time = " + 
                       (new Date().getTime() - baseTime));
            //Cause the timer to quit firing events.
            samplingTimer.stop();

            //Instantiate a Plotter object, which will
            // cause the data in the arrays to be plotted.
            new Plotter();
          }else{
            //Get and save a sample from each Alpha
            // object.
            alpha1Data[sampleTime/100] = 
                                        alpha1Obj.value();
            alpha2Data[sampleTime/100] = 
                                        alpha2Obj.value();
            alpha3Data[sampleTime/100] = 
                                        alpha3Obj.value();
            alpha4Data[sampleTime/100] = 
                                        alpha4Obj.value();
            alpha5Data[sampleTime/100] = 
                                        alpha5Obj.value();
            alpha6Data[sampleTime/100] = 
                                        alpha6Obj.value();
          }//end else
          
          //Update the sample time to prepare for 
          // collecting the next sample when the timer
          // fires again.
          sampleTime += 100;

        }//end actionPerformed
      }//end new ActionListener
    );//end new javax.swing.Timer


    //Instantiate new Alpha objects using different
    // constructors and parameter lists. Use them to
    // populate arrays of alpha data.
    alpha1Obj = new Alpha(2,    //cycles
                          5000);//cycle duration up

    alpha2Obj = new Alpha(2,   //cycles
                          1000,//trigger time
                          0,   //phase delay
                          5000,//cycle duration up
                          0,   //ramp duration for up
                          0);  //duration at one

    alpha3Obj = new Alpha(2,   //cycles
                          1000,//trigger time
                          500, //phase delay
                          5000,//cycle duration up
                          0,   //ramp duration for up
                          0);  //duration at one

    alpha4Obj = new Alpha(2,   //cycles
                          Alpha.DECREASING_ENABLE,//mode
                          0,   //trigger time
                          0,   //phase delay
                          0,   //cycle duration up
                          0,   //ramp duration for up
                          0,   //duration at one
                          5000,//cycle duration down
                          0,   //ramp duration for down
                          0);  //duration at zero
 
    alpha5Obj = new Alpha(2,   //cycles
                          0,   //trigger time
                          0,   //phase delay
                          5000,//cycle duration up
                          2000,//ramp duration for up
                          0);  //duration at one

    alpha6Obj = new Alpha(2,   //cycles
                          Alpha.INCREASING_ENABLE //mode
                                | Alpha.DECREASING_ENABLE,
                          0,   //trigger time
                          0,   //phase delay
                          2500,//cycle duration up
                          1000,//ramp duration for up
                          0,   //duration at one
                          2500,//cycle duration down
                          1000,//ramp duration for down
                          0);  //duration at zero


    //Get the start time from the system clock to be used
    // later to compute the actual elapsed time as a
    // quality check.
    baseTime = new Date().getTime();
    samplingTimer.start();

  }//end constructor
  //===================================================//


  //This is an inner plotting class
  class Plotter extends JFrame{

    //Define plotting parameters.
    double xMin = 0.0;
    double xMax = 100.0;
    double yMin = -10.0;
    double yMax = 100.0;

    //Tic mark intervals
    double xTicInt = 10.0;
    double yTicInt = 10.0;

    //Tic mark lengths.
    double xTicLen = (yMax-yMin)/20;
    double yTicLen = (xMax-xMin)/20;

    //Calculation interval along x-axis
    double xCalcInc = 1.0;

    //Misc instance variables
    int frmWidth = 408;
    int frmHeight = 700;
    int width;
    int height;
    int numberPlots = 6;

    //Plots are drawn on the canvases
    // in this array.
    Canvas[] canvases;

    //Constructor
    Plotter(){
      //Create array to hold correct
      // number of Canvas objects.
      canvases = new Canvas[numberPlots];

      //Create a panel to contain the
      // Canvas objects.  They will be
      // displayed in a one-column grid.
      JPanel canvasPanel = new JPanel();
      canvasPanel.setLayout(//?rows,1 col
                    new GridLayout(0,1));

      //Create a custom Canvas object for
      // each function to be plotted and
      // add them to the one-column grid.
      // Make background colors alternate
      // between white and gray.
      for(int cnt = 0;cnt < numberPlots;cnt++){
        switch(cnt){
          case 0 :
            canvases[cnt] = new MyCanvas(cnt);
            canvases[cnt].setBackground(Color.WHITE);
            break;
          case 1 :
            canvases[cnt] = new MyCanvas(cnt);
            canvases[cnt].setBackground(Color.LIGHT_GRAY);
            break;
          case 2 :
            canvases[cnt] = new MyCanvas(cnt);
            canvases[cnt].setBackground(Color.WHITE);
            break;
          case 3 :
            canvases[cnt] = new MyCanvas(cnt);
            canvases[cnt].setBackground(Color.LIGHT_GRAY);
            break;
          case 4 :
            canvases[cnt] = new MyCanvas(cnt);
            canvases[cnt].setBackground(Color.WHITE);
            break;
          case 5 :
            canvases[cnt] = new MyCanvas(cnt);
            canvases[cnt].setBackground(Color.LIGHT_GRAY);
            break;
        }//end switch
        //Add the object to the grid.
        canvasPanel.add(canvases[cnt]);
      }//end for loop

      //Add the sub-assemblies to the
      // frame.  Set its location, size,
      // and title, and make it visible.
      getContentPane().
               add(canvasPanel,"Center");

      setBounds(0,0,frmWidth,frmHeight);
      setTitle("Copyright 2007, " +
                   "Richard G. Baldwin");
      setVisible(true);

      //Set to exit on X-button click
      setDefaultCloseOperation(
                          EXIT_ON_CLOSE);

      //Get and save the size of the
      // plotting surface
      width = canvases[0].getWidth();
      height = canvases[0].getHeight();

      //Guarantee a repaint on startup.
      for(int cnt = 0;
               cnt < numberPlots; cnt++){
        canvases[cnt].repaint();
      }//end for loop

    }//end constructor
    //---------------------------------//


    //This is an inner class, which is used
    // to override the paint method on the
    // plotting surface.
    class MyCanvas extends Canvas{
      int cnt;//object number
      //Factors to convert from double
      // values to integer pixel locations.
      double xScale;
      double yScale;

      MyCanvas(int cnt){//save obj number
        this.cnt = cnt;
      }//end constructor

      //Override the paint method
      public void paint(Graphics g){
        //Calculate the scale factors
        xScale = width/(xMax-xMin);
        yScale = height/(yMax-yMin);

        //Set the origin based on the
        // minimum values in x and y
        g.translate((int)((0-xMin)*xScale),
                   (int)((0-yMin)*yScale));
        drawAxes(g);//Draw the axes
        g.setColor(Color.BLACK);

        //Get initial data values
        double xVal = xMin;
        int oldX = getTheX(xVal);
        int oldY = 0;
        //Use the Canvas obj number to
        // determine which method to
        // invoke to get the value for y.

        //Now loop and plot the points
        while(xVal < xMax){
          int yVal = 0;
          //Get next data value.  Use the
          // Canvas obj number to
          // determine which array to query
          // to get the value for y

          switch(cnt){
            case 0 :
              yVal =
                getTheY((int)(100*alpha1Data[(int)xVal]));
              break;
            case 1 :
              yVal =
                getTheY((int)(100*alpha2Data[(int)xVal]));
              break;
            case 2 :
              yVal =
                getTheY((int)(100*alpha3Data[(int)xVal]));
              break;
            case 3 :
              yVal =
                getTheY((int)(100*alpha4Data[(int)xVal]));
              break;
            case 4 :
              yVal =
                getTheY((int)(100*alpha5Data[(int)xVal]));
              break;
            case 5 :
              yVal =
                getTheY((int)(100*alpha6Data[(int)xVal]));
          }//end switch1

          //Convert the x-value to an int
          // and draw the next line segment
          int x = getTheX(xVal);
          g.drawLine(oldX,oldY,x,yVal);

          //Increment along the x-axis
          xVal += xCalcInc;

          //Save end point to use as start
          // point for next line segment.
          oldX = x;
          oldY = yVal;
        }//end while loop

      }//end overridden paint method
      //---------------------------------//

      //Method to draw axes with tic marks
      // and labels in the color RED
      void drawAxes(Graphics g){
        g.setColor(Color.RED);

        //Lable left x-axis and bottom
        // y-axis.  These are the easy
        // ones.  Separate the labels from
        // the ends of the tic marks by
        // two pixels.
        g.drawString("" + (int)xMin,
                     getTheX(xMin),
                     getTheY(xTicLen/2)-2);
        g.drawString("" + (int)yMin,
                      getTheX(yTicLen/2)+2,
                            getTheY(yMin));

        //Label the right x-axis and the
        // top y-axis.  These are the hard
        // ones because the position must
        // be adjusted by the font size and
        // the number of characters.
        //Get the width of the string for
        // right end of x-axis and the
        // height of the string for top of
        // y-axis
        //Create a string that is an
        // integer representation of the
        // label for the right end of the
        // x-axis.  Then get a character
        // array that represents the
        // string.
        int xMaxInt = (int)xMax;
        String xMaxStr = "" + xMaxInt;
        char[] array = xMaxStr.
                             toCharArray();

        //Get a FontMetrics object that can
        // be used to get the size of the
        // string in pixels.
        FontMetrics fontMetrics =
                        g.getFontMetrics();
        //Get a bounding rectangle for the
        // string
        Rectangle2D r2d =
               fontMetrics.getStringBounds(
                   array,0,array.length,g);
        //Get the width and the height of
        // the bounding rectangle.  The
        // width is the width of the label
        // at the right end of the
        // x-axis.  The height applies to
        // all the labels, but is needed
        // specifically for the label at
        // the top end of the y-axis.
        int labWidth =
                     (int)(r2d.getWidth());
        int labHeight =
                    (int)(r2d.getHeight());

        //Label the positive x-axis and the
        // positive y-axis using the width
        // and height from above to
        // position the labels.  These
        // labels apply to the very ends of
        // the axes at the edge of the
        // plotting surface.
        g.drawString("" + (int)xMax,
                    getTheX(xMax)-labWidth,
                     getTheY(xTicLen/2)-2);
        g.drawString("" + (int)yMax,
                  getTheX(yTicLen/2)+2,
                  getTheY(yMax)+labHeight);

        //Draw the axes
        g.drawLine(getTheX(xMin),
                             getTheY(0.0),
                             getTheX(xMax),
                             getTheY(0.0));

        g.drawLine(getTheX(0.0),
                            getTheY(yMin),
                            getTheX(0.0),
                            getTheY(yMax));

        //Draw the tic marks on axes
        xTics(g);
        yTics(g);
      }//end drawAxes

      //---------------------------------//

      //Method to draw tic marks on x-axis
      void xTics(Graphics g){
        double xDoub = 0;
        int x = 0;

        //Get the ends of the tic marks.
        int topEnd = getTheY(xTicLen/2);
        int bottomEnd =
                       getTheY(-xTicLen/2);

        //If the vertical size of the
        // plotting area is small, the
        // calculated tic size may be too
        // small.  In that case, set it to
        // 10 pixels.
        if(topEnd < 5){
          topEnd = 5;
          bottomEnd = -5;
        }//end if

        //Loop and draw a series of short
        // lines to serve as tic marks.
        // Begin with the positive x-axis
        // moving to the right from zero.
        while(xDoub < xMax){
          x = getTheX(xDoub);
          g.drawLine(x,topEnd,x,bottomEnd);
          xDoub += xTicInt;
        }//end while

        //Now do the negative x-axis moving
        // to the left from zero
        xDoub = 0;
        while(xDoub > xMin){
          x = getTheX(xDoub);
          g.drawLine(x,topEnd,x,bottomEnd);
          xDoub -= xTicInt;
        }//end while

      }//end xTics
      //---------------------------------//

      //Method to draw tic marks on y-axis
      void yTics(Graphics g){
        double yDoub = 0;
        int y = 0;
        int rightEnd = getTheX(yTicLen/2);
        int leftEnd = getTheX(-yTicLen/2);

        //Loop and draw a series of short
        // lines to serve as tic marks.
        // Begin with the positive y-axis
        // moving up from zero.
        while(yDoub < yMax){
          y = getTheY(yDoub);
          g.drawLine(rightEnd,y,leftEnd,y);
          yDoub += yTicInt;
        }//end while

        //Now do the negative y-axis moving
        // down from zero.
        yDoub = 0;
        while(yDoub > yMin){
          y = getTheY(yDoub);
          g.drawLine(rightEnd,y,leftEnd,y);
          yDoub -= yTicInt;
        }//end while

      }//end yTics

      //---------------------------------//

      //This method translates and scales
      // a double y value to plot properly
      // in the integer coordinate system.
      // In addition to scaling, it causes
      // the positive direction of the
      // y-axis to be from bottom to top.
      int getTheY(double y){
        double yDoub = (yMax+yMin)-y;
        int yInt = (int)(yDoub*yScale);
        return yInt;
      }//end getTheY
      //---------------------------------//

      //This method scales a double x value
      // to plot properly in the integer
      // coordinate system.
      int getTheX(double x){
        return (int)(x*xScale);
      }//end getTheX
      //---------------------------------//

    }//end inner class MyCanvas
    //===================================//

  }//end inner class Plotter
  //====================================================//

/*public static void main(String[] args)
	 
{
	     
Java3D007 j= new Java3D007();	 } */
}//end class Java3D007
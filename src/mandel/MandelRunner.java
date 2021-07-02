package mandel;

import complex.*;
import processing.core.PApplet;
import processing.core.PVector;

public class MandelRunner {

	private PApplet app;
	
	private int maxIterations;
	private double maxModulus;
	
	private ComplexNumber exponent;
	
	private PVector origin;
	private float zoom;
	
	private int nThreads;
	private int nThreadsComplete;
	
	private Runnable onThreadsAllComplete;
	
	public MandelRunner() {}
	
	public void run() {
		app.clear();
		app.resetMatrix();
		
		app.loadPixels();
		
		nThreadsComplete = 0;
		
		double threadWorkload = (float)app.height / nThreads;
		for (int i = 0; i < nThreads; i++) {
			int yStart = (int)Math.floor(i * threadWorkload);
			int yEnd = (int)Math.floor((i + 1) * threadWorkload);
			
			MandelCalculator thread = new MandelCalculator(yStart, yEnd, this); 
			thread.start(); 
        }
	}
	
	void onThreadComplete() {
		nThreadsComplete++;
		
		app.updatePixels();
		
		if (threadsAllComplete()) {
			onThreadsAllComplete.run();
		}
	}
	
	public boolean threadsAllComplete() {
		return nThreadsComplete >= nThreads;
	}
	
	public PVector pointFromPixel(float x, float y) {
		return new PVector((x - app.width / 2f) / zoom + origin.x, -(y - app.height / 2f) / zoom + origin.y);
	}
	
	public int colorFromNIterations(int nIterations) {
		return app.color(PApplet.TAU / 2, 1, 1 - (float)nIterations / maxIterations);
	}
	
	// getters/setters
	
	public PApplet app() {
		return app;
	}
	public MandelRunner app(PApplet app) {
		this.app = app;
		return this;
	}

	public int maxIterations() {
		return maxIterations;
	}
	public MandelRunner maxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
		return this;
	}

	public double maxModulus() {
		return maxModulus;
	}
	public MandelRunner maxModulus(double maxModulus) {
		this.maxModulus = maxModulus;
		return this;
	}

	public PVector origin() {
		return origin;
	}
	public MandelRunner origin(PVector origin) {
		this.origin = origin;
		return this;
	}

	public float zoom() {
		return zoom;
	}
	public MandelRunner zoom(float zoom) {
		this.zoom = zoom;
		return this;
	}

	public int nThreads() {
		return nThreads;
	}
	public MandelRunner nThreads(int nThreads) {
		this.nThreads = nThreads;
		return this;
	}
	
	public int nThreadsComplete() {
		return nThreadsComplete;
	}

	public ComplexNumber exponent() {
		return exponent;
	}
	public MandelRunner exponent(ComplexNumber exponent) {
		if (exponent == null) {
			throw new IllegalArgumentException("null argument");
		}
		this.exponent = exponent;
		return this;
	}
	
	public MandelRunner onThreadsAllComplete(Runnable callback) {
		onThreadsAllComplete = callback;
		return this;
	}

}

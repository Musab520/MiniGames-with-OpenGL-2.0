
import EventListeners.DifferenceEventListener;
import EventListeners.OriginalEventListener;
import Shapes.BrushPoints;
import Shapes.Ellipse;
import Shapes.Line;
import Shapes.Painting;
import Shapes.Polygon;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	protected static OriginalEventListener renderPaintingCanvas, renderOriginalCanvas, renderJigsawCanvas;
	protected static OriginalEventListener renderDisplay1Canvas, renderDisplay2Canvas, renderDisplay3Canvas;
	protected static DrawingToolEventListener renderDrawingToolGLCanvas;
	protected static DifferenceEventListener renderDifferenceGLCanvas;
	protected static GLCanvas display1, display2, display3, painting, originalCanvas, differenceGLCanvas,
			jigsawGLCanvas, drawingToolGLCanvas;

	protected static MainFrame mf;

	protected ArrayList<Painting> paintings = new ArrayList<Painting>();

	protected JTextPane remainingPiecesText, remainingMovesText, remainingTriesText;
	protected JLabel timeLimitLabel, remainingDifferencesLabel;
	protected int remainingDifferences, remainingPieces, timeLimit, remainingMoves, remainingTries = 3;
	private int counterSelected;

	PuzzlePanel puzzlepanel = new PuzzlePanel();
	private JPanel contentPane;
	private JPanel colorPanel;
	boolean startingFlag = true;

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/* ENTER JPANEL */

		JPanel EnterJPanel = new JPanel();
		EnterJPanel.setBounds(0, 0, 1300, 872);
		contentPane.add(EnterJPanel);
		EnterJPanel.setLayout(null);
		EnterJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		EnterJPanel.setBackground(Color.WHITE);

		JButton enterButton = new JButton("ENTER GALLERY MUSEUM");
		enterButton.setOpaque(false);
		enterButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		enterButton.setFocusTraversalKeysEnabled(false);
		enterButton.setContentAreaFilled(false);
		enterButton.setBorderPainted(false);
		enterButton.setBounds(351, 283, 623, 115);
		EnterJPanel.add(enterButton);

		JButton enterToolsButton = new JButton("ENTER GALLERY TOOLS");
		enterToolsButton.setOpaque(false);
		enterToolsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		enterToolsButton.setFocusTraversalKeysEnabled(false);
		enterToolsButton.setContentAreaFilled(false);
		enterToolsButton.setBorderPainted(false);
		enterToolsButton.setBounds(351, 417, 596, 115);
		EnterJPanel.add(enterToolsButton);

		JButton quitButton = new JButton("QUIT");
		quitButton.setOpaque(false);
		quitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		quitButton.setFocusTraversalKeysEnabled(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setBorderPainted(false);
		quitButton.setBounds(532, 544, 249, 115);
		EnterJPanel.add(quitButton);

		/* MAIN PAGE JPANEL */

		JPanel MainJPanel = new JPanel();
		MainJPanel.setVisible(false);
		MainJPanel.setBackground(Color.WHITE);
		MainJPanel.setBounds(0, 0, 1300, 872);
		MainJPanel.setLayout(null);
		MainJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(MainJPanel);

		JButton paintingInfoButton = new JButton("PAINTING INFO");
		paintingInfoButton.setFocusTraversalKeysEnabled(false);
		paintingInfoButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		paintingInfoButton.setOpaque(false);
		paintingInfoButton.setContentAreaFilled(false);
		paintingInfoButton.setBorderPainted(false);
		paintingInfoButton.setBounds(456, 598, 430, 93);
		MainJPanel.add(paintingInfoButton);

		JButton enterGameModeButton = new JButton("ENTER GAME MODE");
		enterGameModeButton.setOpaque(false);
		enterGameModeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		enterGameModeButton.setFocusTraversalKeysEnabled(false);
		enterGameModeButton.setContentAreaFilled(false);
		enterGameModeButton.setBorderPainted(false);
		enterGameModeButton.setBounds(429, 703, 492, 99);
		MainJPanel.add(enterGameModeButton);

		JButton nextPaintingButton = new JButton("NEXT PAINTING");
		nextPaintingButton.setOpaque(false);
		nextPaintingButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		nextPaintingButton.setFocusTraversalKeysEnabled(false);
		nextPaintingButton.setContentAreaFilled(false);
		nextPaintingButton.setBorderPainted(false);
		nextPaintingButton.setBounds(1007, 629, 234, 56);
		MainJPanel.add(nextPaintingButton);

		JButton btnPreviousPainting = new JButton("PREV PAINTING");
		btnPreviousPainting.setOpaque(false);
		btnPreviousPainting.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		btnPreviousPainting.setFocusTraversalKeysEnabled(false);
		btnPreviousPainting.setContentAreaFilled(false);
		btnPreviousPainting.setBorderPainted(false);
		btnPreviousPainting.setBounds(53, 629, 234, 56);
		MainJPanel.add(btnPreviousPainting);

		JTextPane txtpnArrowUpHere = new JTextPane();
		txtpnArrowUpHere.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
		txtpnArrowUpHere.setText("↑");
		txtpnArrowUpHere.setEditable(false);
		txtpnArrowUpHere.setBounds(641, 536, 34, 56);
		MainJPanel.add(txtpnArrowUpHere);

		JButton backButton1 = new JButton("BACK");
		backButton1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		backButton1.setBounds(53, 786, 123, 56);
		backButton1.setFocusTraversalKeysEnabled(false);
		backButton1.setOpaque(false);
		backButton1.setContentAreaFilled(false);
		backButton1.setBorderPainted(false);
		MainJPanel.add(backButton1);

		JPanel drawingPanel1 = new JPanel(new BorderLayout());
		drawingPanel1.setBounds(34, 34, 400, 500);
		MainJPanel.add(drawingPanel1);

		JPanel drawingPanel2 = new JPanel(new BorderLayout());
		drawingPanel2.setBounds(446, 34, 400, 500);
		MainJPanel.add(drawingPanel2);

		JPanel drawingPanel3 = new JPanel(new BorderLayout());
		drawingPanel3.setBounds(858, 34, 400, 500);
		MainJPanel.add(drawingPanel3);

		/* PAINTING INFO JPANEL */

		JPanel PaintingInfoJPanel = new JPanel();
		PaintingInfoJPanel.setVisible(false);
		PaintingInfoJPanel.setBounds(0, 0, 1300, 872);
		PaintingInfoJPanel.setLayout(null);
		PaintingInfoJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		PaintingInfoJPanel.setBackground(Color.WHITE);
		contentPane.add(PaintingInfoJPanel);

		JPanel drawingPanel4 = new JPanel(new BorderLayout());
		drawingPanel4.setBounds(70, 34, 600, 700);
		PaintingInfoJPanel.add(drawingPanel4);

		JButton backButton2 = new JButton("BACK");
		backButton2.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		backButton2.setBounds(53, 786, 123, 56);
		backButton2.setFocusTraversalKeysEnabled(false);
		backButton2.setOpaque(false);
		backButton2.setContentAreaFilled(false);
		backButton2.setBorderPainted(false);
		PaintingInfoJPanel.add(backButton2);

		JTextPane titleText = new JTextPane();
		titleText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		titleText.setText("TITLE: Composition with Red, Yellow, and Blue");
		titleText.setBounds(739, 34, 516, 72);
		titleText.setEditable(false);
		PaintingInfoJPanel.add(titleText);

		JTextPane artistText = new JTextPane();
		artistText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		artistText.setText("ARTIST: Piet Mondrian");
		artistText.setBounds(739, 141, 516, 46);
		artistText.setEditable(false);
		PaintingInfoJPanel.add(artistText);

		JTextPane createdText = new JTextPane();
		createdText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		createdText.setText("CREATED: 1929");
		createdText.setBounds(739, 206, 516, 46);
		createdText.setEditable(false);
		PaintingInfoJPanel.add(createdText);

		JTextPane nationalityText = new JTextPane();
		nationalityText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		nationalityText.setText("NATIONALITY: DUTCH");
		nationalityText.setBounds(739, 270, 516, 46);
		nationalityText.setEditable(false);
		PaintingInfoJPanel.add(nationalityText);

		JLabel lblDescriptionl = new JLabel("DESCRIPTION:");
		lblDescriptionl.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblDescriptionl.setBounds(739, 338, 230, 34);
		PaintingInfoJPanel.add(lblDescriptionl);

		JTextPane descriptionText = new JTextPane();
		descriptionText.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		descriptionText.setText(
				"It consists of thick, black brushwork, defining the borders of coloured geometric figures. Mondrian's use of the term “composition” (the organization of forms on the canvas) signals his experimentation with abstract arrangements. Mondrian had returned home to the Netherlands just prior to the outbreak of the First World War and would remain there until the war ended. While in the Netherlands he further developed his style, ruling out compositions that were either too static or too dynamic, concluding that asymmetrical arrangements of geometric (rather than organic) shapes in primary (rather than secondary) colors best represent universal forces. Moreover, he combined his development of an abstract style with his interest in philosophy, spirituality, and his belief that the evolution of abstraction was a sign of humanity’s progress.");
		descriptionText.setBounds(739, 384, 516, 350);
		descriptionText.setEditable(false);
		PaintingInfoJPanel.add(descriptionText);

		/* GAME MODE OPTIONS JPANEL */

		JPanel GameModeOptionsJPanel = new JPanel();
		GameModeOptionsJPanel.setVisible(false);
		GameModeOptionsJPanel.setBounds(0, 0, 1300, 872);
		GameModeOptionsJPanel.setLayout(null);
		GameModeOptionsJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GameModeOptionsJPanel.setBackground(Color.WHITE);
		contentPane.add(GameModeOptionsJPanel);

		JTextPane txtpnPlayDifferenceGame = new JTextPane();
		txtpnPlayDifferenceGame.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		txtpnPlayDifferenceGame.setText("PLAY DIFFERENCE GAME");
		txtpnPlayDifferenceGame.setBounds(125, 121, 530, 64);
		txtpnPlayDifferenceGame.setEditable(false);
		GameModeOptionsJPanel.add(txtpnPlayDifferenceGame);

		JTextPane txtpnSpotTheDifferences = new JTextPane();
		txtpnSpotTheDifferences.setText("SPOT THE DIFFERENCES BETWEEN THE ORIGINAL PAINTING AND IT'S FAKE!");
		txtpnSpotTheDifferences.setBounds(688, 140, 485, 28);
		txtpnSpotTheDifferences.setEditable(false);
		GameModeOptionsJPanel.add(txtpnSpotTheDifferences);

		JTextPane txtpnPleaseSelectMode_1 = new JTextPane();
		txtpnPleaseSelectMode_1.setText("PLEASE SELECT MODE:");
		txtpnPleaseSelectMode_1.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		txtpnPleaseSelectMode_1.setEditable(false);
		txtpnPleaseSelectMode_1.setBounds(125, 263, 485, 64);
		GameModeOptionsJPanel.add(txtpnPleaseSelectMode_1);

		JButton FiveDifferencesButton = new JButton("5");
		FiveDifferencesButton.setOpaque(false);
		FiveDifferencesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		FiveDifferencesButton.setFocusTraversalKeysEnabled(false);
		FiveDifferencesButton.setContentAreaFilled(false);
		FiveDifferencesButton.setBorderPainted(false);
		FiveDifferencesButton.setBounds(671, 227, 164, 115);
		GameModeOptionsJPanel.add(FiveDifferencesButton);

		JButton SevenDifferencesButton = new JButton("7");
		SevenDifferencesButton.setOpaque(false);
		SevenDifferencesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		SevenDifferencesButton.setFocusTraversalKeysEnabled(false);
		SevenDifferencesButton.setContentAreaFilled(false);
		SevenDifferencesButton.setBorderPainted(false);
		SevenDifferencesButton.setBounds(863, 227, 164, 115);
		GameModeOptionsJPanel.add(SevenDifferencesButton);

		JButton TenDifferencesButton = new JButton("10");
		TenDifferencesButton.setOpaque(false);
		TenDifferencesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		TenDifferencesButton.setFocusTraversalKeysEnabled(false);
		TenDifferencesButton.setContentAreaFilled(false);
		TenDifferencesButton.setBorderPainted(false);
		TenDifferencesButton.setBounds(1049, 227, 164, 115);
		GameModeOptionsJPanel.add(TenDifferencesButton);

		JLabel lblDifferences = new JLabel("Differences");
		lblDifferences.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblDifferences.setBounds(703, 344, 118, 16);
		GameModeOptionsJPanel.add(lblDifferences);

		JLabel lblDifferences_1 = new JLabel("Differences");
		lblDifferences_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblDifferences_1.setBounds(896, 344, 118, 16);
		GameModeOptionsJPanel.add(lblDifferences_1);

		JLabel lblDifferences_2 = new JLabel("Differences");
		lblDifferences_2.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblDifferences_2.setBounds(1082, 344, 118, 16);
		GameModeOptionsJPanel.add(lblDifferences_2);

		JTextPane txtpnPlayJigzawPuzzle = new JTextPane();
		txtpnPlayJigzawPuzzle.setText("PLAY SHUFFLE PUZZLE GAME");
		txtpnPlayJigzawPuzzle.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		txtpnPlayJigzawPuzzle.setBounds(125, 416, 583, 64);
		txtpnPlayJigzawPuzzle.setEditable(false);
		GameModeOptionsJPanel.add(txtpnPlayJigzawPuzzle);

		JTextPane txtpnThePaintingIs = new JTextPane();
		txtpnThePaintingIs.setText(
				"The painting is cut into various pieces of different shapes that have to be fitted together.");
		txtpnThePaintingIs.setBounds(728, 425, 485, 41);
		txtpnThePaintingIs.setEditable(false);
		GameModeOptionsJPanel.add(txtpnThePaintingIs);

		JTextPane txtpnPleaseSelectMode = new JTextPane();
		txtpnPleaseSelectMode.setText("PLEASE SELECT MODE:");
		txtpnPleaseSelectMode.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		txtpnPleaseSelectMode.setEditable(false);
		txtpnPleaseSelectMode.setBounds(125, 573, 485, 64);
		GameModeOptionsJPanel.add(txtpnPleaseSelectMode);

		JButton NinePiecesButton = new JButton("9");
		NinePiecesButton.setOpaque(false);
		NinePiecesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		NinePiecesButton.setFocusTraversalKeysEnabled(false);
		NinePiecesButton.setContentAreaFilled(false);
		NinePiecesButton.setBorderPainted(false);
		NinePiecesButton.setBounds(671, 526, 164, 115);
		GameModeOptionsJPanel.add(NinePiecesButton);

		JButton ThirtySixPiecesButton = new JButton("36");
		ThirtySixPiecesButton.setOpaque(false);
		ThirtySixPiecesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		ThirtySixPiecesButton.setFocusTraversalKeysEnabled(false);
		ThirtySixPiecesButton.setContentAreaFilled(false);
		ThirtySixPiecesButton.setBorderPainted(false);
		ThirtySixPiecesButton.setBounds(863, 531, 164, 115);
		GameModeOptionsJPanel.add(ThirtySixPiecesButton);

		JButton EightyOnePiecesButton = new JButton("81");
		EightyOnePiecesButton.setOpaque(false);
		EightyOnePiecesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 42));
		EightyOnePiecesButton.setFocusTraversalKeysEnabled(false);
		EightyOnePiecesButton.setContentAreaFilled(false);
		EightyOnePiecesButton.setBorderPainted(false);
		EightyOnePiecesButton.setBounds(1049, 531, 164, 115);
		GameModeOptionsJPanel.add(EightyOnePiecesButton);

		JLabel lblPieces = new JLabel("Pieces");
		lblPieces.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblPieces.setBounds(725, 649, 63, 16);
		GameModeOptionsJPanel.add(lblPieces);

		JLabel lblDifferences_1_1 = new JLabel("Pieces");
		lblDifferences_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblDifferences_1_1.setBounds(915, 649, 63, 16);
		GameModeOptionsJPanel.add(lblDifferences_1_1);

		JLabel lblDifferences_2_1 = new JLabel("Pieces");
		lblDifferences_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblDifferences_2_1.setBounds(1104, 649, 63, 16);
		GameModeOptionsJPanel.add(lblDifferences_2_1);

		JButton backButton3 = new JButton("BACK");
		backButton3.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		backButton3.setBounds(53, 786, 123, 56);
		backButton3.setFocusTraversalKeysEnabled(false);
		backButton3.setOpaque(false);
		backButton3.setContentAreaFilled(false);
		backButton3.setBorderPainted(false);
		GameModeOptionsJPanel.add(backButton3);

		/* DIFFERENCE GAME JPANEL */

		JPanel DifferenceJPanel = new JPanel();
		DifferenceJPanel.setVisible(false);
		DifferenceJPanel.setLayout(null);
		DifferenceJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		DifferenceJPanel.setBounds(0, 0, 1300, 872);
		contentPane.add(DifferenceJPanel);

		JPanel drawingPanel5 = new JPanel(new BorderLayout());
		drawingPanel5.setBounds(34, 62, 600, 700);
		DifferenceJPanel.add(drawingPanel5);

		JPanel drawingPanel6 = new JPanel(new BorderLayout());
		drawingPanel6.setBounds(678, 62, 600, 700);
		DifferenceJPanel.add(drawingPanel6);

		JButton backButton4 = new JButton("BACK");
		backButton4.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		backButton4.setBounds(53, 786, 123, 56);
		backButton4.setFocusTraversalKeysEnabled(false);
		backButton4.setOpaque(false);
		backButton4.setContentAreaFilled(false);
		backButton4.setBorderPainted(false);
		DifferenceJPanel.add(backButton4);

		JLabel timeLimitLabel = new JLabel("TIME LIMIT: 0s");
		timeLimitLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		timeLimitLabel.setBounds(311, 794, 303, 34);
		DifferenceJPanel.add(timeLimitLabel);

		JLabel remainingDifferencesLabel = new JLabel("REMAINING DIFFERENCES: 5");
		remainingDifferencesLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		remainingDifferencesLabel.setBounds(737, 794, 428, 34);
		DifferenceJPanel.add(remainingDifferencesLabel);

		JLabel lblOriginal = new JLabel("ORIGINAL");
		lblOriginal.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblOriginal.setBounds(266, 16, 147, 34);
		DifferenceJPanel.add(lblOriginal);

		JLabel lblFake = new JLabel("FAKE");
		lblFake.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblFake.setBounds(948, 16, 93, 34);
		DifferenceJPanel.add(lblFake);

		/* JIGSAW PUZZLE JPANEL */

		JPanel JigsawPuzzleJPanel = new JPanel();
		JigsawPuzzleJPanel.setVisible(false);
		JigsawPuzzleJPanel.setBounds(0, 0, 1300, 872);
		JigsawPuzzleJPanel.setLayout(null);
		JigsawPuzzleJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JigsawPuzzleJPanel.setBackground(Color.WHITE);
		contentPane.add(JigsawPuzzleJPanel);

		JPanel drawingPanel7 = new JPanel(new BorderLayout());
		drawingPanel7.setBounds(35, 34, 600, 700);
		JigsawPuzzleJPanel.add(drawingPanel7);

		JTextPane remainingPiecesText = new JTextPane();
		remainingPiecesText.setText("REMAINING PIECES: 9");
		remainingPiecesText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		remainingPiecesText.setEditable(false);
		remainingPiecesText.setBounds(814, 187, 453, 42);
		JigsawPuzzleJPanel.add(remainingPiecesText);

		JTextPane remainingMovesText = new JTextPane();
		remainingMovesText.setText("REMAINING MOVES: 9");
		remainingMovesText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		remainingMovesText.setEditable(false);
		remainingMovesText.setBounds(814, 304, 453, 42);
		JigsawPuzzleJPanel.add(remainingMovesText);

		JTextPane remainingTriesText = new JTextPane();
		remainingTriesText.setText("REMAINING TRIES: 3");
		remainingTriesText.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		remainingTriesText.setEditable(false);
		remainingTriesText.setBounds(814, 418, 453, 42);
		JigsawPuzzleJPanel.add(remainingTriesText);

		JButton startButton = new JButton("START");
		startButton.setOpaque(false);
		startButton.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		startButton.setFocusTraversalKeysEnabled(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		startButton.setBounds(878, 536, 172, 64);
		JigsawPuzzleJPanel.add(startButton);

		JButton submitButton = new JButton("SUBMIT");
		submitButton.setOpaque(false);
		submitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		submitButton.setFocusTraversalKeysEnabled(false);
		submitButton.setContentAreaFilled(false);
		submitButton.setBorderPainted(false);
		submitButton.setBounds(878, 625, 172, 64);
		JigsawPuzzleJPanel.add(submitButton);

		JButton backButton5 = new JButton("BACK");
		backButton5.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		backButton5.setBounds(53, 786, 123, 56);
		backButton5.setFocusTraversalKeysEnabled(false);
		backButton5.setOpaque(false);
		backButton5.setContentAreaFilled(false);
		backButton5.setBorderPainted(false);
		JigsawPuzzleJPanel.add(backButton5);

		/* TOOLS JPANEL */

		JPanel toolsJPanel = new JPanel();
		toolsJPanel.setVisible(false);
		toolsJPanel.setBounds(0, 0, 1300, 872);
		toolsJPanel.setLayout(null);
		toolsJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolsJPanel.setBackground(Color.WHITE);
		contentPane.add(toolsJPanel);

		JPanel drawingPanel8 = new JPanel(new BorderLayout());
		drawingPanel8.setBounds(35, 34, 600, 700);
		toolsJPanel.add(drawingPanel8);

		JTextPane txtpnDrawingTools = new JTextPane();
		txtpnDrawingTools.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		txtpnDrawingTools.setText("DRAWING TOOLS");
		txtpnDrawingTools.setBounds(811, 20, 257, 42);
		toolsJPanel.add(txtpnDrawingTools);

		JButton btnBrush = new JButton("Brush");
		btnBrush.setOpaque(false);
		btnBrush.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnBrush.setFocusTraversalKeysEnabled(false);
		btnBrush.setContentAreaFilled(false);
		btnBrush.setBorderPainted(false);
		btnBrush.setBounds(736, 103, 151, 94);
		toolsJPanel.add(btnBrush);

		JButton btnLines = new JButton("Line");
		btnLines.setOpaque(false);
		btnLines.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnLines.setFocusTraversalKeysEnabled(false);
		btnLines.setContentAreaFilled(false);
		btnLines.setBorderPainted(false);
		btnLines.setBounds(736, 233, 151, 94);
		toolsJPanel.add(btnLines);

		JButton btnPolygon = new JButton("Polygon");
		btnPolygon.setOpaque(false);
		btnPolygon.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnPolygon.setFocusTraversalKeysEnabled(false);
		btnPolygon.setContentAreaFilled(false);
		btnPolygon.setBorderPainted(false);
		btnPolygon.setBounds(985, 233, 151, 94);
		toolsJPanel.add(btnPolygon);

		JButton btnEllipse = new JButton("Ellipses");
		btnEllipse.setOpaque(false);
		btnEllipse.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnEllipse.setFocusTraversalKeysEnabled(false);
		btnEllipse.setContentAreaFilled(false);
		btnEllipse.setBorderPainted(false);
		btnEllipse.setBounds(985, 103, 151, 94);
		toolsJPanel.add(btnEllipse);

		JTextPane txtpnBrushMode = new JTextPane();
		txtpnBrushMode.setText("ADVANCED OPTIONS");
		txtpnBrushMode.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		txtpnBrushMode.setBounds(746, 339, 331, 42);
		toolsJPanel.add(txtpnBrushMode);

		JTextPane txtpnBrushThickness = new JTextPane();
		txtpnBrushThickness.setText("Brush thickness:");
		txtpnBrushThickness.setBounds(746, 411, 114, 24);
		toolsJPanel.add(txtpnBrushThickness);

		JTextPane txtpnColor = new JTextPane();
		txtpnColor.setText("Color:");
		txtpnColor.setBounds(746, 447, 56, 24);
		toolsJPanel.add(txtpnColor);

		JSlider BrushThicknessSlider = new JSlider();
		BrushThicknessSlider.setBounds(884, 406, 190, 29);
		BrushThicknessSlider.setValue(20);
		toolsJPanel.add(BrushThicknessSlider);

		JLabel NewLabel_1 = new JLabel("R");
		NewLabel_1.setFont(new Font("Andale Mono", Font.BOLD, 20));
		NewLabel_1.setBounds(770, 483, 18, 16);
		toolsJPanel.add(NewLabel_1);

		JSlider redSlider = new JSlider() {
			@Override
			public void updateUI() {
				setUI(new CustomSliderUI(this, new Color(255, 0, 0)));
			}
		};
		redSlider.setBackground(Color.WHITE);
		redSlider.setMaximum(255);
		redSlider.setBorder(null);
		redSlider.setBounds(845, 471, 210, 29);
		toolsJPanel.add(redSlider);

		JTextField redTextField = new JTextField();
		redTextField.setToolTipText("Please enter a value between 0..255");
		redTextField.setText("50");
		redTextField.setHorizontalAlignment(SwingConstants.CENTER);
		redTextField.setColumns(10);
		redTextField.setBorder(new LineBorder(Color.BLACK));
		redTextField.setBackground(Color.WHITE);
		redTextField.setBounds(1065, 471, 64, 26);
		toolsJPanel.add(redTextField);

		JSlider greenSlider = new JSlider() {
			@Override
			public void updateUI() {
				setUI(new CustomSliderUI(this, new Color(0, 255, 0)));
			}
		};
		greenSlider.setBackground(Color.WHITE);
		greenSlider.setMaximum(255);
		greenSlider.setBounds(844, 530, 210, 29);
		toolsJPanel.add(greenSlider);

		JLabel NewLabel_2 = new JLabel("G");
		NewLabel_2.setFont(new Font("Andale Mono", Font.BOLD, 20));
		NewLabel_2.setBounds(770, 542, 18, 16);
		toolsJPanel.add(NewLabel_2);

		JTextField greenTextField = new JTextField();
		greenTextField.setToolTipText("Please enter a value between 0..255");
		greenTextField.setText("50");
		greenTextField.setHorizontalAlignment(SwingConstants.CENTER);
		greenTextField.setColumns(10);
		greenTextField.setBorder(new LineBorder(Color.BLACK));
		greenTextField.setBackground(Color.WHITE);
		greenTextField.setBounds(1065, 533, 64, 26);
		toolsJPanel.add(greenTextField);

		JLabel NewLabel_3 = new JLabel("B");
		NewLabel_3.setFont(new Font("Andale Mono", Font.BOLD, 20));
		NewLabel_3.setBounds(770, 606, 18, 16);
		toolsJPanel.add(NewLabel_3);

		JSlider blueSlider = new JSlider() {
			@Override
			public void updateUI() {
				setUI(new CustomSliderUI(this, new Color(0, 0, 255)));
			}
		};
		blueSlider.setBackground(Color.WHITE);
		blueSlider.setMaximum(255);
		blueSlider.setBounds(844, 596, 210, 29);
		toolsJPanel.add(blueSlider);

		JTextField blueTextField = new JTextField();
		blueTextField.setToolTipText("Please enter a value between 0..255");
		blueTextField.setText("50");
		blueTextField.setHorizontalAlignment(SwingConstants.CENTER);
		blueTextField.setColumns(10);
		blueTextField.setBorder(new LineBorder(Color.BLACK));
		blueTextField.setBackground(Color.WHITE);
		blueTextField.setBounds(1065, 591, 64, 26);
		toolsJPanel.add(blueTextField);

		JLabel lblColorPreview = new JLabel("Color Preview");
		lblColorPreview.setFont(new Font("Andale Mono", Font.PLAIN, 14));
		lblColorPreview.setBounds(875, 646, 104, 16);
		toolsJPanel.add(lblColorPreview);

		colorPanel = new JPanel();
		colorPanel.setBorder(new LineBorder(Color.BLACK));
		colorPanel.setBackground(new Color(50, 50, 50));
		colorPanel.setBounds(831, 674, 210, 115);
		toolsJPanel.add(colorPanel);

		JButton clearDrawingCanvasButton = new JButton("CLEAR");
		clearDrawingCanvasButton.setOpaque(false);
		clearDrawingCanvasButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		clearDrawingCanvasButton.setFocusTraversalKeysEnabled(false);
		clearDrawingCanvasButton.setContentAreaFilled(false);
		clearDrawingCanvasButton.setBorderPainted(false);
		clearDrawingCanvasButton.setBounds(1149, 770, 123, 56);
		toolsJPanel.add(clearDrawingCanvasButton);

		JButton saveButton = new JButton("SAVE");
		saveButton.setOpaque(false);
		saveButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		saveButton.setFocusTraversalKeysEnabled(false);
		saveButton.setContentAreaFilled(false);
		saveButton.setBorderPainted(false);
		saveButton.setBounds(1149, 690, 123, 56);
		toolsJPanel.add(saveButton);

		JButton backButton6 = new JButton("BACK");
		backButton6.setBounds(53, 786, 123, 56);
		backButton6.setOpaque(false);
		backButton6.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		backButton6.setFocusTraversalKeysEnabled(false);
		backButton6.setContentAreaFilled(false);
		backButton6.setBorderPainted(false);
		toolsJPanel.add(backButton6);

		/* GL CANVAS INTO JPANEL */

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		display1 = new GLCanvas(capabilities);
		Animator animator1 = new Animator(display1);
		renderDisplay1Canvas = new OriginalEventListener();
		display1.addGLEventListener(renderDisplay1Canvas);
		display1.setSize(400, 500);
		drawingPanel1.add(display1);
		animator1.start();
		display1.requestFocus();

		display2 = new GLCanvas(capabilities);
		Animator animator2 = new Animator(display2);
		renderDisplay2Canvas = new OriginalEventListener();
		display2.addGLEventListener(renderDisplay2Canvas);
		display2.setSize(400, 500);
		drawingPanel2.add(display2);
		animator2.start();
		display2.requestFocus();

		display3 = new GLCanvas(capabilities);
		Animator animator3 = new Animator(display3);
		renderDisplay3Canvas = new OriginalEventListener();
		display3.addGLEventListener(renderDisplay3Canvas);
		display3.setSize(400, 500);
		drawingPanel3.add(display3);
		animator3.start();
		display3.requestFocus();

		painting = new GLCanvas(capabilities);
		Animator animator = new Animator(painting);
		renderPaintingCanvas = new OriginalEventListener();
		painting.addGLEventListener(renderPaintingCanvas);
		drawingPanel4.add(painting);
		animator.start();
		painting.requestFocus();

		originalCanvas = new GLCanvas(capabilities);
		Animator animator4 = new Animator(originalCanvas);
		renderOriginalCanvas = new OriginalEventListener();
		originalCanvas.addGLEventListener(renderOriginalCanvas);
		drawingPanel5.add(originalCanvas);
		animator4.start();
		originalCanvas.requestFocus();

		differenceGLCanvas = new GLCanvas(capabilities);
		Animator animator5 = new Animator(differenceGLCanvas);
		renderDifferenceGLCanvas = new DifferenceEventListener();
		differenceGLCanvas.addGLEventListener(renderDifferenceGLCanvas);
		drawingPanel6.add(differenceGLCanvas);
		animator5.start();
		differenceGLCanvas.requestFocus();

		jigsawGLCanvas = new GLCanvas(capabilities);
		Animator animator6 = new Animator(jigsawGLCanvas);
		renderJigsawCanvas = new OriginalEventListener();
		jigsawGLCanvas.addGLEventListener(renderJigsawCanvas);
		drawingPanel7.add(jigsawGLCanvas);
		animator6.start();
		jigsawGLCanvas.requestFocus();

		drawingToolGLCanvas = new GLCanvas(capabilities);
		Animator animator7 = new Animator(drawingToolGLCanvas);
		renderDrawingToolGLCanvas = new DrawingToolEventListener();
		drawingToolGLCanvas.addGLEventListener(renderDrawingToolGLCanvas);
		drawingToolGLCanvas.addMouseListener(renderDrawingToolGLCanvas);
		drawingToolGLCanvas.addMouseMotionListener(renderDrawingToolGLCanvas);
		drawingPanel8.add(drawingToolGLCanvas);
		animator7.start();
		drawingToolGLCanvas.requestFocus();

		/* BUTTON ACTIONS */

		/* CANVAS LISTENERS */

		differenceGLCanvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = 700 - e.getY();
				Painting p = renderDifferenceGLCanvas.getPainting();
				for (int i = 0; i < p.getList().size(); i++) {
					if (p.getList().get(i).returnShapeType().compareTo("Polygon") == 0) {
						Polygon poly = (Polygon) p.getList().get(i);
						if (poly.isDifferent() && poly.isInside(x, y) && !poly.isClicked()) {
							poly.setClicked(true);
							remainingDifferences--;
							remainingDifferencesLabel.setText("REMAINING DIFFERENCES: " + remainingDifferences);
						}
					} else if (p.getList().get(i).returnShapeType().compareTo("Ellipse") == 0) {
						Ellipse el = (Ellipse) p.getList().get(i);
						if (el.isDifferent() && el.IsInside(x, y) && !el.isClicked()) {
							el.setClicked(true);
							remainingDifferences--;
							remainingDifferencesLabel.setText("REMAINING DIFFERENCES: " + remainingDifferences);
						}
					}
				}
				differenceGLCanvas.display();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		/* ENTER BUTTONS */

		enterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// sound click
				soundClick("Mouse Click");
				EnterJPanel.setVisible(false);
				MainJPanel.setVisible(true);

				if (startingFlag) {
					/* initialize paintings here */
					initialize();
					renderDisplay1Canvas.setPainting(paintings.get(2));
					renderDisplay2Canvas.setPainting(paintings.get(0));
					renderDisplay3Canvas.setPainting(paintings.get(1));

					startingFlag = false;
				}
				System.out.println(paintings.size());
			}
		});

		enterToolsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// sound click
				soundClick("Mouse Click");

				MainJPanel.setVisible(true);

				if (startingFlag) {
					/* initialize paintings here */
					initialize();
					renderDisplay1Canvas.setPainting(paintings.get(2));
					renderDisplay2Canvas.setPainting(paintings.get(0));
					renderDisplay3Canvas.setPainting(paintings.get(1));

					startingFlag = false;
				}

				MainJPanel.setVisible(false);
				drawingPanel1.remove(0);
				drawingPanel2.remove(0);
				drawingPanel3.remove(0);

				EnterJPanel.setVisible(false);
				toolsJPanel.setVisible(true);
			}
		});

		quitButton.addActionListener(e -> {
			soundClick("Mouse Click");
			System.exit(0);
		});

		/* MAIN BUTTONS */

		paintingInfoButton.addActionListener(e -> {

			// sound click
			soundClick("Mouse Click");
			PaintingInfoJPanel.setVisible(true);
			MainJPanel.setVisible(false);

			drawingPanel1.remove(0);
			drawingPanel2.remove(0);
			drawingPanel3.remove(0);

			renderPaintingCanvas.setPainting(paintings.get(counterSelected));

			titleText.setText("TITLE: " + paintings.get(counterSelected).getTitle());
			artistText.setText("ARTIST: " + paintings.get(counterSelected).getArtist());
			createdText.setText("CREATED: " + paintings.get(counterSelected).getYear());
			nationalityText.setText("NATIONALITY: " + paintings.get(counterSelected).getNationality());
			descriptionText.setText("DESCRIPTION: " + paintings.get(counterSelected).getDescription());
			descriptionText.setText(paintings.get(counterSelected).getDescription());
		});

		enterGameModeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// sound click
				soundClick("Mouse Click");
				GameModeOptionsJPanel.setVisible(true);
				MainJPanel.setVisible(false);

				drawingPanel1.remove(0);
				drawingPanel2.remove(0);
				drawingPanel3.remove(0);
				drawingPanel4.remove(0);
			}
		});

		nextPaintingButton.addActionListener(e -> {
			soundClick("Mouse Click");

			counterSelected++;

			if (counterSelected > paintings.size() - 1)
				counterSelected = 0;

			System.out.println("selected--> " + counterSelected);

			if (counterSelected == 0)
				renderDisplay1Canvas.setPainting(paintings.get(paintings.size() - 1));
			else
				renderDisplay1Canvas.setPainting(paintings.get(counterSelected - 1));
			renderDisplay2Canvas.setPainting(paintings.get(counterSelected));
			if (counterSelected == paintings.size() - 1)
				renderDisplay3Canvas.setPainting(paintings.get(0));
			else
				renderDisplay3Canvas.setPainting(paintings.get(counterSelected + 1));

			renderPaintingCanvas.setPainting(paintings.get(counterSelected));
			renderOriginalCanvas.setPainting(paintings.get(counterSelected));
			renderDifferenceGLCanvas.setPainting(paintings.get(counterSelected));
			renderJigsawCanvas.setPainting(paintings.get(counterSelected));

		});

		btnPreviousPainting.addActionListener(e -> {
			soundClick("Mouse Click");
			counterSelected--;
			if (counterSelected < 0)
				counterSelected = paintings.size() - 1;

			System.out.println("selected--> " + counterSelected);

			if (counterSelected == 0)
				renderDisplay1Canvas.setPainting(paintings.get(paintings.size() - 1));
			else
				renderDisplay1Canvas.setPainting(paintings.get(counterSelected - 1));
			renderDisplay2Canvas.setPainting(paintings.get(counterSelected));
			if (counterSelected == paintings.size() - 1)
				renderDisplay3Canvas.setPainting(paintings.get(0));
			else
				renderDisplay3Canvas.setPainting(paintings.get(counterSelected + 1));

			renderPaintingCanvas.setPainting(paintings.get(counterSelected));
			renderOriginalCanvas.setPainting(paintings.get(counterSelected));
			renderDifferenceGLCanvas.setPainting(paintings.get(counterSelected));
			renderJigsawCanvas.setPainting(paintings.get(counterSelected));
		});

		backButton1.addActionListener(e -> {
			soundClick("Mouse Click");
			EnterJPanel.setVisible(true);
			MainJPanel.setVisible(false);
		});

		/* PAINTING INFO BUTTONS */

		backButton2.addActionListener(e -> {
			soundClick("Mouse Click");
			MainJPanel.setVisible(true);
			PaintingInfoJPanel.setVisible(false);

			drawingPanel1.add(display1);
			drawingPanel2.add(display2);
			drawingPanel3.add(display3);
		});

		/* GAME MODE OPTIONS BUTTONS */

		FiveDifferencesButton.addActionListener(e -> {
			soundClick("Mouse Click");
			timeLimitLabel.setText("TIME LIMIT: " + 30 * 5 + "s");
			remainingDifferences = 5;
			remainingDifferencesLabel.setText("REMAINING DIFFERENCES: " + remainingDifferences);
			DifferenceJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);
			renderDifferenceGLCanvas = new DifferenceEventListener();
			renderDifferenceGLCanvas.setN(remainingDifferences);
			renderOriginalCanvas.setPainting(paintings.get(counterSelected));
			renderDifferenceGLCanvas.setPainting(paintings.get(counterSelected));
			differenceGLCanvas.addGLEventListener(renderDifferenceGLCanvas);
		});

		SevenDifferencesButton.addActionListener(e -> {
			soundClick("Mouse Click");
			timeLimitLabel.setText("TIME LIMIT: " + 30 * 7 + "s");
			remainingDifferences = 7;
			remainingDifferencesLabel.setText("REMAINING DIFFERENCES: " + remainingDifferences);
			DifferenceJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);
			renderDifferenceGLCanvas = new DifferenceEventListener();
			renderDifferenceGLCanvas.setN(remainingDifferences);
			renderOriginalCanvas.setPainting(paintings.get(counterSelected));
			renderDifferenceGLCanvas.setPainting(paintings.get(counterSelected));
			differenceGLCanvas.addGLEventListener(renderDifferenceGLCanvas);
		});

		TenDifferencesButton.addActionListener(e -> {
			soundClick("Mouse Click");
			timeLimitLabel.setText("TIME LIMIT: " + 30 * 10 + "s");
			remainingDifferences = 10;
			remainingDifferencesLabel.setText("REMAINING DIFFERENCES: " + remainingDifferences);
			DifferenceJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);
			renderDifferenceGLCanvas = new DifferenceEventListener();
			renderDifferenceGLCanvas.setN(remainingDifferences);
			renderOriginalCanvas.setPainting(paintings.get(counterSelected));
			renderDifferenceGLCanvas.setPainting(paintings.get(counterSelected));
			differenceGLCanvas.addGLEventListener(renderDifferenceGLCanvas);
		});

		NinePiecesButton.addActionListener(e -> {
			soundClick("Mouse Click");
			remainingPieces = 9;
			remainingTries = 3;
			remainingMoves = remainingPieces + 3;
			renderJigsawCanvas.setPainting(paintings.get(counterSelected));
			remainingPiecesText.setText("REMAINING PIECES: " + remainingPieces);
			remainingMovesText.setText("REMAINING MOVES: " + remainingMoves);
			remainingTriesText.setText("REMAINING TRIES: " + remainingTries);
			JigsawPuzzleJPanel.removeAll();
			JigsawPuzzleJPanel.add(remainingPiecesText);
			JigsawPuzzleJPanel.add(remainingMovesText);
			JigsawPuzzleJPanel.add(remainingTriesText);
			JigsawPuzzleJPanel.add(submitButton);
			JigsawPuzzleJPanel.add(backButton5);
			JigsawPuzzleJPanel.add(startButton);
			JigsawPuzzleJPanel.add(drawingPanel7);
			JigsawPuzzleJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);
		});

		ThirtySixPiecesButton.addActionListener(e -> {
			soundClick("Mouse Click");
			remainingPieces = 36;
			remainingTries = 3;
			remainingMoves = remainingPieces + 3;
			renderJigsawCanvas.setPainting(paintings.get(counterSelected));
			remainingPiecesText.setText("REMAINING PIECES: " + remainingPieces);
			remainingMovesText.setText("REMAINING MOVES: " + remainingMoves);
			remainingTriesText.setText("REMAINING TRIES: " + remainingTries);
			JigsawPuzzleJPanel.removeAll();
			JigsawPuzzleJPanel.add(remainingPiecesText);
			JigsawPuzzleJPanel.add(remainingMovesText);
			JigsawPuzzleJPanel.add(remainingTriesText);
			JigsawPuzzleJPanel.add(submitButton);
			JigsawPuzzleJPanel.add(backButton5);
			JigsawPuzzleJPanel.add(startButton);
			JigsawPuzzleJPanel.add(drawingPanel7);
			JigsawPuzzleJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);
		});

		EightyOnePiecesButton.addActionListener(e -> {
			soundClick("Mouse Click");
			remainingPieces = 81;
			remainingTries = 3;
			remainingMoves = remainingPieces + 3;
			renderJigsawCanvas.setPainting(paintings.get(counterSelected));
			remainingPiecesText.setText("REMAINING PIECES: " + remainingPieces);
			remainingMovesText.setText("REMAINING MOVES: " + remainingMoves);
			remainingTriesText.setText("REMAINING TRIES: " + remainingTries);
			JigsawPuzzleJPanel.removeAll();
			JigsawPuzzleJPanel.add(remainingPiecesText);
			JigsawPuzzleJPanel.add(remainingMovesText);
			JigsawPuzzleJPanel.add(remainingTriesText);
			JigsawPuzzleJPanel.add(submitButton);
			JigsawPuzzleJPanel.add(backButton5);
			JigsawPuzzleJPanel.add(startButton);
			JigsawPuzzleJPanel.add(drawingPanel7);
			JigsawPuzzleJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);
		});

		startButton.addActionListener(e -> {
			soundClick("Mouse Click");

			takeSnapShot(drawingPanel7);
			JigsawPuzzleJPanel.removeAll();
			JigsawPuzzleJPanel.add(remainingPiecesText);
			JigsawPuzzleJPanel.add(remainingMovesText);
			JigsawPuzzleJPanel.add(remainingTriesText);
			JigsawPuzzleJPanel.add(submitButton);
			JigsawPuzzleJPanel.add(backButton5);
			JigsawPuzzleJPanel.add(startButton);
			puzzlepanel = new PuzzlePanel(remainingPieces, new File("Painting.jpg"));
			puzzlepanel.CreatePuzzlePanel(JigsawPuzzleJPanel);
		});

		submitButton.addActionListener(e -> {
			soundClick("Mouse Click");
			remainingTries--;
			if (remainingTries < 0) {
				remainingTriesText.setText("REMAINING TRIES: GAME OVER");
				return;
			}
			if (!puzzlepanel.check())
				remainingTriesText.setText("REMAINING TRIES: " + remainingTries);
			else {
				remainingTriesText.setText("REMAINING TRIES: GREAT JOB");
			}
		});

		backButton3.addActionListener(e -> {
			soundClick("Mouse Click");
			MainJPanel.setVisible(true);
			GameModeOptionsJPanel.setVisible(false);

			drawingPanel1.add(display1);
			drawingPanel2.add(display2);
			drawingPanel3.add(display3);
			drawingPanel4.add(painting);
		});

		/* DIFFERENCE GAME BUTTONS */

		backButton4.addActionListener(e -> {
			soundClick("Mouse Click");
			GameModeOptionsJPanel.setVisible(true);
			DifferenceJPanel.setVisible(false);
		});

		/* JIGSAW PUZZLE BUTTONS */

		backButton5.addActionListener(e -> {
			soundClick("Mouse Click");
			GameModeOptionsJPanel.setVisible(true);
			JigsawPuzzleJPanel.setVisible(false);
		});

		/* TOOLS BUTTONS */

		btnBrush.addActionListener(e -> {
			soundClick("Mouse Click");
			renderDrawingToolGLCanvas.shape = "BrushPoints";
		});

		btnLines.addActionListener(e -> {
			soundClick("Mouse Click");
			renderDrawingToolGLCanvas.shape = "Line";
		});

		btnPolygon.addActionListener(e -> {
			soundClick("Mouse Click");
			renderDrawingToolGLCanvas.shape = "Polygon";
		});

		btnEllipse.addActionListener(e -> {
			soundClick("Mouse Click");
			renderDrawingToolGLCanvas.shape = "Ellipse";
		});

		redSlider.addChangeListener((ChangeListener) e -> {
			redTextField.setText(redSlider.getValue() + "");
			colorPanel.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
		});

		greenSlider.addChangeListener((ChangeListener) e -> {
			greenTextField.setText(greenSlider.getValue() + "");
			colorPanel.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
		});

		blueSlider.addChangeListener((ChangeListener) e -> {
			blueTextField.setText(blueSlider.getValue() + "");
			colorPanel.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
		});

		BrushThicknessSlider.addChangeListener((ChangeListener) e -> {
			renderDrawingToolGLCanvas.brushRadius = BrushThicknessSlider.getValue();
		});

		redTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if (redTextField.getText().isEmpty()) {
					redSlider.setValue(0);
					redTextField.setText(0 + "");
					JOptionPane.showMessageDialog(null, "Your input is empty! Please enter a number.");
				} else if (Integer.parseInt(redTextField.getText()) < 0) {
					redSlider.setValue(0);
					redTextField.setText(0 + "");
					JOptionPane.showMessageDialog(null, "Your is less than 0! Please increse your desired number.");
				} else if (Integer.parseInt(redTextField.getText()) > 255) {
					redSlider.setValue(255);
					redTextField.setText(255 + "");
					JOptionPane.showMessageDialog(null, "Your number exceeds 255! Please lower your desired number.");
				} else
					redSlider.setValue(Integer.parseInt(redTextField.getText()));

			}
		});

		greenTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if (greenTextField.getText().isEmpty()) {
					greenSlider.setValue(0);
					greenTextField.setText(0 + "");
					JOptionPane.showMessageDialog(null, "Your input is empty! Please enter a number.");
				} else if (Integer.parseInt(greenTextField.getText()) < 0) {
					greenSlider.setValue(0);
					greenTextField.setText(0 + "");
					JOptionPane.showMessageDialog(null, "Your is less than 0! Please increse your desired number.");
				} else if (Integer.parseInt(greenTextField.getText()) > 255) {
					greenSlider.setValue(255);
					greenTextField.setText(255 + "");
					JOptionPane.showMessageDialog(null, "Your number exceeds 255! Please lower your desired number.");
				} else
					greenSlider.setValue(Integer.parseInt(greenTextField.getText()));
			}
		});

		blueTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if (blueTextField.getText().isEmpty()) {
					blueSlider.setValue(0);
					blueTextField.setText(0 + "");
					JOptionPane.showMessageDialog(null, "Your input is empty! Please enter a number.");
				} else if (Integer.parseInt(blueTextField.getText()) < 0) {
					blueSlider.setValue(0);
					blueTextField.setText(0 + "");
					JOptionPane.showMessageDialog(null, "Your is less than 0! Please increse your desired number.");
				} else if (Integer.parseInt(blueTextField.getText()) > 255) {
					blueSlider.setValue(255);
					blueTextField.setText(255 + "");
					JOptionPane.showMessageDialog(null, "Your number exceeds 255! Please lower your desired number.");
				} else
					blueSlider.setValue(Integer.parseInt(blueTextField.getText()));
			}
		});

		clearDrawingCanvasButton.addActionListener(e -> {
			soundClick("Mouse Click");
			renderDrawingToolGLCanvas.clearCanvas();

			redSlider.setValue(50);
			redTextField.setText(50 + "");

			greenSlider.setValue(50);
			greenTextField.setText(50 + "");

			blueSlider.setValue(50);
			blueTextField.setText(50 + "");
		});

		saveButton.addActionListener(e -> {
			soundClick("Mouse Click");
			Painting newPainting = new Painting();
			for (int i = 0; i < renderDrawingToolGLCanvas.painting.getList().size(); i++) {
				if (renderDrawingToolGLCanvas.painting.getList().get(i).returnShapeType().equals("Polygon")) {
					Polygon poly = (Polygon) renderDrawingToolGLCanvas.painting.getList().get(i);
					poly = new Polygon(poly.getX(), poly.getY(), poly.getCx(), poly.getCy(), poly.getN(),
							poly.getColor(), poly.getGl());
					newPainting.getList().add(poly);
				} else if (renderDrawingToolGLCanvas.painting.getList().get(i).returnShapeType().equals("Ellipse")) {
					Ellipse el = (Ellipse) renderDrawingToolGLCanvas.painting.getList().get(i);
					el = new Ellipse(el.getxCenter(), el.getyCenter(), el.getXradius(), el.getYradius(), el.getColor(),
							el.getGl());
					newPainting.getList().add(el);
				} else if (renderDrawingToolGLCanvas.painting.getList().get(i).returnShapeType().equals("Line")) {
					Line line = (Line) renderDrawingToolGLCanvas.painting.getList().get(i);
					line = new Line(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(),
							line.getGl());
					newPainting.getList().add(line);
				} else if (renderDrawingToolGLCanvas.painting.getList().get(i).returnShapeType()
						.equals("BrushPoints")) {
					BrushPoints brushPoints = (BrushPoints) renderDrawingToolGLCanvas.painting.getList().get(i);
					brushPoints = new BrushPoints(brushPoints.getBrushPointsList(), brushPoints.getRadius(),
							brushPoints.getGl(), brushPoints.getColor());
					newPainting.getList().add(brushPoints);
				}
			}
			paintings.add(newPainting);
			renderDisplay1Canvas.painting = paintings.get(paintings.size() - 1);
		});

		backButton6.addActionListener(e -> {
			soundClick("Mouse Click");
			EnterJPanel.setVisible(true);
			toolsJPanel.setVisible(false);
			drawingPanel1.add(display1);
			drawingPanel2.add(display2);
			drawingPanel3.add(display3);
		});
	}

	public void initialize() {

		display1.display();
		display2.display();
		display3.display();
		painting.display();
		originalCanvas.display();
		differenceGLCanvas.display();
		jigsawGLCanvas.display();

		Painting painting = new Painting();

		/* initialize paintings here */

		// dummy by Musab
		int[] x = { 0, 90 * 2, 90 * 2, 0 };
		int[] y = { 0, 0, 90 * 2, 90 * 2 };

		// bottom left red square
		Polygon square1 = new Polygon(x, y, 90, 90, 4, Color.RED, display1.getGL().getGL2());
		painting.getList().add(square1);

		// bottom blue circle
		Ellipse e1 = new Ellipse(140 * 2, 50 * 2, 50 * 2, 50 * 2, Color.BLUE, display1.getGL().getGL2());
		painting.getList().add(e1);

		// bottom yellow pentagon
		int[] x2 = { 190 * 2, 290 * 2, 290 * 2, 240 * 2, 190 * 2 };
		int[] y2 = { 0, 0, 50 * 2, 90 * 2, 50 * 2 };
		Polygon penta1 = new Polygon(x2, y2, 480, 90, 5, Color.YELLOW, display1.getGL().getGL2());
		painting.getList().add(penta1);

		// middle left green triangle
		int[] x3 = { 0, 90 * 2, 90 * 2 };
		int[] y3 = { 145 * 2, 190 * 2, 100 * 2 };
		Polygon tri1 = new Polygon(x3, y3, 90, 145 * 2, 3, Color.GREEN, display1.getGL().getGL2());
		painting.getList().add(tri1);

		// mid cyan hexagon pentagon
		int[] x4 = { 120 * 2, 160 * 2, 180 * 2, 160 * 2, 120 * 2, 100 * 2 };
		int[] y4 = { 100 * 2, 100 * 2, 145 * 2, 190 * 2, 190 * 2, 145 * 2 };
		Polygon hexa1 = new Polygon(x4, y4, 280, 145 * 2, 6, Color.CYAN, display1.getGL().getGL2());
		painting.getList().add(hexa1);

		// middle right magenta triangle
		int[] x5 = { 190 * 2, 280 * 2, 235 * 2 };
		int[] y5 = { 100 * 2, 100 * 2, 190 * 2 };
		Polygon tri2 = new Polygon(x5, y5, 470, 145 * 2, 3, Color.MAGENTA, display1.getGL().getGL2());
		painting.getList().add(tri2);

		// top orange ellipse
		Ellipse e2 = new Ellipse(145 * 2, 245 * 2, 145 * 2, 50 * 2, Color.ORANGE, display1.getGL().getGL2());
		painting.getList().add(e2);

		painting.setTitle("DEMO PAINTING");
		painting.setArtist("Musab Abu Aasi");
		painting.setYear("2021");
		painting.setNationality("Amurrikkan");
		painting.setDescription("Made out of sheer desperation, this painting represents suffering and despair.");

		paintings.add(painting);

		/* Composition in Red, Green, and Blue */

		painting = new Painting();

		square1 = new Polygon(x, y, 90, 90, 4, Color.RED, display1.getGL().getGL2());
		painting.getList().add(square1);

		painting.setTitle("Composition with Red, Yellow, and Blue");
		painting.setArtist("Piet Mondrian");
		painting.setYear("1929");
		painting.setNationality("DUTCH");
		painting.setDescription(
				"It consists of thick, black brushwork, defining the borders of coloured geometric figures. Mondrian's use of the term “composition” (the organization of forms on the canvas) signals his experimentation with abstract arrangements. Mondrian had returned home to the Netherlands just prior to the outbreak of the First World "
						+ "War and would remain there until the war ended. While in the Netherlands he further developed his style, ruling out compositions that were either too static or too dynamic, concluding that asymmetrical arrangements of geometric (rather than organic) shapes in primary (rather than secondary) colors best represent universal forces. Moreover, "
						+ "he combined his development of an abstract style with his interest in philosophy, spirituality, and his belief that the evolution of abstraction was a sign of humanity’s progress.");

		paintings.add(painting);

		/* Squares with Concentric Circles */

		painting = new Painting();

		e1 = new Ellipse(140 * 2, 50 * 2, 50 * 2, 50 * 2, Color.BLUE, display1.getGL().getGL2());
		painting.getList().add(e1);

		painting.setTitle("Squares with Concentric Circles");
		painting.setArtist("Wassily Kandinsky");
		painting.setYear("1913");
		painting.setNationality("Russian");
		painting.setDescription(
				"Squares with Concentric Circles (Farbstudie - Quadrate und konzentrische Ringe), perhaps, Kandinsky's most recognizable work, is not actually a full-fledged picture. This drawing is a small study on how different colour combinations are perceived that the painter used in his creative process as a support material. "
						+ "For Kandinsky, colour meant more than just a visual component of a picture. Colour is its soul. In his books, he described his own perspective on how colours interacted with each other and with the spectator in detail and very poetically. Moreover, Kandinsky was a synaesthete, i.e. he could ‘hear colours’ and ‘see sounds.’ So, this is probably "
						+ "ighteous that after a century, it is not one of his compositions – which he himself considered as his main achievements – but this small drawing that has become one of Kandinsky’s most popular works.");

		paintings.add(painting);

		/* Red Poppy */

		painting.setTitle("Red Poppy");
		painting.setArtist("Georgia OKeeffe");
		painting.setYear("1927");
		painting.setNationality("American");
		painting.setDescription(
				"Georgia O'Keeffe was a key pioneer in the emergence of a uniquely American form of modern art. After early studies in both Chicago and New York during the first decade of the twentieth century, O'Keeffe took up teaching posts in South Carolina and Texas before returning to New York in 1918, Here, her career was supported by Alfred Stieglitz, the "
						+ "influential owner of a gallery called 291, whom she married in 1924, In the mid 1920s O'Keeffe was initially influenced by the Precisionists, and produced a series of hard-edged works representing New York skyscrapers, such as New York Street with Moon, It was also around this time that she started to produce near-abstract paintings based upon natural forms including plants, "
						+ "flowers and shells."
						+ " Red Poppy provides a key example of this approach. Here the entire canvas is filled with soft, undulating, forms, executed in luminous reds and blacks.");

		/* Starry Nights */

		painting.setTitle("DEMO PAINTING");
		painting.setArtist("Musab Abu Aasi");
		painting.setYear("2021");
		painting.setNationality("Amurrikkan");
		painting.setDescription("Made out of sheer desperation, this painting represents suffering and despair.");

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					mf = frame;
					frame.setVisible(true);
					frame.setTitle("Museum Game Simulation");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void takeSnapShot(JPanel panel) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String format = "jpg";
		String fileName = "Painting." + format;

		Rectangle screenRect = new Rectangle(143, 165, panel.getSize().width, panel.getSize().height);
		BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
		try {
			ImageIO.write(screenFullImage, format, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] getRGB() {
		int[] results = new int[3];
		results[0] = colorPanel.getBackground().getRed();
		results[1] = colorPanel.getBackground().getGreen();
		results[2] = colorPanel.getBackground().getBlue();
		return results;
	}

	private void soundClick(String soundType) {
		// sound click
		ClassLoader CLDR = this.getClass().getClassLoader();
		InputStream soundName = null;
		if (soundType.equals("Mouse Click"))
			soundName = CLDR.getResourceAsStream("audio/mouse click.wav");
		else if (soundType.equals("Correct"))
			soundName = CLDR.getResourceAsStream("audio/mouse click.wav");
		else if (soundType.equals("Wrong"))
			soundName = CLDR.getResourceAsStream("audio/mouse click.wav");
		// System.out.println("beep");
	}
}

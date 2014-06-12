package controller;

import controller.object.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import model.bean.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class EventHandler implements Initializable
{
	@FXML
	private AnchorPane main_page;
	@FXML
	private AnchorPane LexiconInfo;
	@FXML
	private AnchorPane ReciteSetting;
	@FXML
	private AnchorPane Reciting;
	@FXML
	private ChoiceBox<String> LexiconSelection;
	@FXML
	private Label LexiconName;
	@FXML
	private Label TotalCnt;
	@FXML
	private Label RecitedCnt;
	@FXML
	private Label RightCnt;
	@FXML
	private Label WrongCnt;
	@FXML
	private Label Accuracy;
	@FXML
	private Label MemoryWord;//�ϴα��е��ĵ�����ʾ
	/*@FXML  
	private TextField StartEntry;//��ʼ���������
*/	@FXML
	private TextField ReciteNum;//���и������ÿ�
	@FXML
	private ToggleGroup myToggleGroup;
	@FXML
	private RadioButton rb0;
	@FXML
	private RadioButton rb1;
	@FXML
	private RadioButton rb2;
	@FXML
	private Label ChMean;
	@FXML
	private Label EngWord;
	@FXML 
	private TextField EngInput;
	@FXML
	private Group ErrorHint;
	@FXML
	private Group RecitingGroup;
	@FXML
	private AnchorPane ShowMessageDialog;
	@FXML
	private AnchorPane EndHint;
	@FXML
	private AnchorPane RecitationRes;
	@FXML
	private Label AlertMessage;
	@FXML
	private Label Res_Lexicon;
	@FXML
	private Label Res_ReciteNum;
	@FXML
	private Label Res_Right;
	@FXML
	private Label Res_Wrong;
	@FXML
	private Label Res_Accuracy;
	@SuppressWarnings("rawtypes")
	@FXML
	private ComboBox StartEntry0;
	@FXML
	private ChoiceBox<String> ChartType;//ͼ����ʾ
	@FXML
	private PieChart InfoPieChart1;
	@FXML
	private PieChart InfoPieChart2;
	@FXML
	private PieChart InfoPieChart3;
	@FXML
	private PieChart InfoPieChart4;
	@FXML
	private Group PieChartGroup;
	@SuppressWarnings("rawtypes")
	@FXML
	private BarChart InfoBarChart;
	@FXML
	private CategoryAxis XAxis;
	@FXML
	private NumberAxis YAxis;
	@FXML
	private Group SignGroup;
	@FXML
	private Group InfoGroup;
	@FXML
	private Group LabelGroup;
	
	private int sig;//���ʱ���������Ƿ����
	
	@FXML  
	private void ReturnMain(Event event){
		this.ShowMainPage();
	}
	
	/*�鿴�ʿ���Ϣ*/
	@FXML  
	private void ReadLexiconInfo(Event event) throws IOException{
		if(this.LexiconSelection.getValue() == null){
			this.ShowAlertDialog("����ô��ѡ�ʿ�q(�s^�t)�r");
		}else{
			String type = this.LexiconSelection.getValue().substring(0, 1);
			if(Action.getInstance().chooseLexicon(type)){
				this.ChartType.getSelectionModel().clearSelection();
				this.InfoBarChart.setVisible(false);
				this.PieChartGroup.setVisible(false);
				this.InitializeReadLexiconInfo();
				this.LexiconInfo.setVisible(true);
				this.main_page.setVisible(false);
			}else{
				this.ShowAlertDialog("��ѽ�r(�s_�t)�q��ʼ���ʿ�ʧ����");
			}			
		}
	}
	
	/*ѡ��ʿ����뱳������ҳ��*/
	@FXML
	private void EnterResiteSettingMode(Event event) throws IOException{
		if(this.LexiconSelection.getValue() == null ){
			this.ShowAlertDialog("����ô��ѡ�ʿ�q(�s^�t)�r");
		}else{
			if(Action.getInstance().chooseLexicon(this.LexiconSelection.getValue().substring(0, 1))){
				this.InitializeReciteSetting();
				this.main_page.setVisible(false);
				this.ReciteSetting.setVisible(true);
			}else{
				this.ShowAlertDialog("��ѽ�r(�s_�t)�q��ʼ���ʿ�ʧ����");
			}	
		}
			
	}
	
	/*��ʼ����*/
	@FXML
	private void StartReciting(Event event) throws IOException{
	
		if(this.myToggleGroup.getSelectedToggle() == null){
			this.ShowAlertDialog("����ô��ѡ��ʼ��ʽ:(");
		}else if(this.ReciteNum.getText().length() == 0){
			this.ShowAlertDialog("����ô�����ñ��и���:(");
		}else if(Integer.parseInt(this.ReciteNum.getText()) == 0){
			this.ShowAlertDialog("������������:(");
			this.ReciteNum.setText("");
		}else{
			Action.getInstance().setNum(Integer.parseInt(this.ReciteNum.getText()));
			Toggle t = this.myToggleGroup.getSelectedToggle();
			Object o = t.getUserData();
			String flag;
			if(o.toString().equals("2")){//�Զ�����ʼ����
				if(this.StartEntry0.getEditor().getText().length()==0){
					this.ShowAlertDialog("����ľ������ʼ����ι���ǾʹӴʿ��һ�����ʿ�ʼ��");
					flag = "0";
				} else
					flag = this.StartEntry0.getEditor().getText();
			}else	
				flag = o.toString();
		
			if(Action.getInstance().chooseWord(flag)){
				this.RecitingGroup.setDisable(false);
				this.ChMean.setText(Word.getInstance().getChinese());
				this.EngInput.setText("");
				this.EngWord.setText(Word.getInstance().getEnglish());
				this.ReciteSetting.setVisible(false);
				this.Reciting.setDisable(false);
				this.Reciting.setVisible(true);
			}else
				this.ShowAlertDialog("��ʼ�����ʱ���ʧ����:(");
		}
	}
	
	/*��һ�����ʱ���*/
	@FXML
	private void NextWord(Event event) throws IOException{
		int flag = Action.getInstance().nextWord(this.EngInput.getText());
		this.sig = flag;
		
		if(flag < 0){//������ʾ��ʾ��Ϣ
			this.RecitingGroup.setDisable(true);
			this.ErrorHint.setVisible(true);
		}else{
			switch(flag){
				case 1:
					this.ChMean.setText(Word.getInstance().getChinese());
					this.EngWord.setText(Word.getInstance().getEnglish());
					this.EngInput.setText("");
					break;
				case 2: 
					this.ShowEndHint();
					break;
				case 3:
					this.ShowAlertDialog("��ȡ��һ������ʧ����:(");
					break;
				default:
					break;
			}
		}
	}
	
	/*��ʾ�����������л��߱��н���*/
	@FXML
	public void ContinueReciting(Event event){
		switch(this.sig){
			case -1:
				this.ErrorHint.setVisible(false);
				this.ChMean.setText(Word.getInstance().getChinese());
				this.EngWord.setText(Word.getInstance().getEnglish());
				this.EngInput.setText("");
				this.RecitingGroup.setDisable(false);	
				break;
			case -2:
				this.ErrorHint.setVisible(false);
				this.ShowEndHint();
				break;
			case -3:
				this.ShowAlertDialog("��ȡ��һ������ʧ��:(");
				break;
			default:
				break;
		}
	}
	
	@FXML
	private void InputCheck(Event event){
		int s = event.toString().indexOf("code");
		String eventStr = event.toString();
		String code = eventStr.substring(s+7, eventStr.length() -1);
		//System.out.println("code: " + code + " " + code.indexOf("DIGIT"));
		if((code.indexOf("DIGIT") == -1 && code.length() == 1)
				|| (code.indexOf("DIGIT") >=0 && code.length() > 7)){
			this.ShowAlertDialog("���������֣�");
			String txt = this.ReciteNum.getText();
			this.ReciteNum.setText(txt.substring(0, txt.length() - 1));
		}
	}
	
	@FXML
	private void ShowRecitationRes(Event event){
		String res[] = Action.getInstance().getRecitedInfo();
		this.Res_Lexicon.setText(res[0]+"�ʿ�");
		this.Res_ReciteNum.setText(res[1]);
		this.Res_Right.setText(res[2]);
		this.Res_Wrong.setText(res[3]);
		this.Res_Accuracy.setText(res[4] + "%");
		this.Reciting.setVisible(false);
		this.RecitationRes.setVisible(true);
	}
	
	
	 @FXML
	 private void OnAlertOKClick(Event event) {
		 this.HideAlertDialog();
	 }

	@SuppressWarnings("unchecked")
	@FXML 
	 private void AutoCompleteText(Event event) throws IOException{
		TextField editor = this.StartEntry0.getEditor();
		int s = event.toString().indexOf("code");
		String eventStr = event.toString();
		String code = eventStr.substring(s+7, eventStr.length() -1);
		if(code.length() == 1 || code.equals("BACK_SPACE")){
			List<String> values = Action.getInstance().search(editor.getText());
			this.StartEntry0.setItems(FXCollections.observableList(values));
		}
	}
	
	@FXML
	private void BarChartZoom(Event event){
		this.ChartZoom(InfoBarChart, true,220,80,375,275);
	}
	
	@FXML
	private void PieChart1Zoom(Event event){
		this.ChartZoom(InfoPieChart1, false,-1,-9,180,155);
	}
	
	@FXML
	private void PieChart2Zoom(Event event){
		this.ChartZoom(InfoPieChart2, false,184,-9,180,155);
	}
	
	@FXML
	private void PieChart3Zoom(Event event){
		this.ChartZoom(InfoPieChart3, false,-1,121,180,155);
	}
	
	@FXML
	private void PieChart4Zoom(Event event){
		this.ChartZoom(InfoPieChart4, false,184,121,180,155);
	}
	
	/*����ͼ������IfBar��layoutλ��x,y�Լ�ͼ����w,h��������*/
	public void ChartZoom(Chart c,boolean IfBar,int x,int y,int w,int h){
		if(c.getLayoutX() == 0 || c.getLayoutX() == -220){//��СΪԭ�ȳߴ�
			this.SignGroup.setVisible(true);
			this.InfoGroup.setVisible(true);
			c.setPrefWidth(w);
			c.setPrefHeight(h);
			c.setLayoutX(x);
			c.setLayoutY(y);
			if(!IfBar){
				this.LabelGroup.setVisible(true);
				this.InfoPieChart1.setVisible(true);
				this.InfoPieChart2.setVisible(true);
				this.InfoPieChart3.setVisible(true);
				this.InfoPieChart4.setVisible(true);
			}
			
		}else{//�Ŵ�
			this.SignGroup.setVisible(false);
			this.InfoGroup.setVisible(false);
			this.PieChartGroup.setVisible(false);
			this.InfoBarChart.setVisible(false);
			
			if(IfBar){
				c.setPrefWidth(600);
				c.setPrefHeight(400);
				//(Scene)c.getParent().getParent().getParent().
				c.setLayoutX(0);
				c.setLayoutY(0);
				this.InfoBarChart.setVisible(true);
			}else{
				c.setPrefWidth(600);
				c.setPrefHeight(400);
				this.LabelGroup.setVisible(false);
				c.setLayoutX(-220);
				c.setLayoutY(-90);
				this.PieChartGroup.setVisible(true);
				this.InfoPieChart1.setVisible(false);
				this.InfoPieChart2.setVisible(false);
				this.InfoPieChart3.setVisible(false);
				this.InfoPieChart4.setVisible(false);
				c.setVisible(true);
			}
		}
		
			
	}
	 
	 public void ShowAlertDialog(String message){
		 this.ReciteSetting.setDisable(true);
		 this.AlertMessage.setText(message);
		 this.AlertMessage.setWrapText(true);
		 this.ShowMessageDialog.setVisible(true);
	 }
	 
	 public void HideAlertDialog(){
		 this.ShowMessageDialog.setVisible(false);
		 this.ReciteSetting.setDisable(false);
	 }
	
	
	/*�ʿ�ѡ�����ݰ�*/
	public void LexiconDataBinding(){
		ObservableList <String> lexicons = FXCollections.observableArrayList();
		for(int i = 0; i < 26; i++){
			char c = (char) ('a' + i);
			lexicons.add(c+"�ʿ�");	
		}
		this.LexiconSelection.setItems(lexicons);
	}
	
	
	/*�趨RadioButton*/
	public void RadioButtonSetting(){
		this.rb0.setUserData("0");
		this.rb1.setUserData("1");
		this.rb2.setUserData("2");
	}
	
	/*�趨ͼ����ʽ*/
	public void ChartTypeSetting(){
		this.ChartType.setItems(FXCollections.observableArrayList("��ͼ","��״ͼ"));
		this.PieChartGroup.setVisible(false);
		this.InfoBarChart.setVisible(false);
		this.ChartType.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>(){

					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number value, Number new_value) {
						// TODO Auto-generated method stub
							if(new_value.equals(0)){
								PieChartGroup.setVisible(true);
								InfoBarChart.setVisible(false);
							}else if(new_value.equals(1)){
								PieChartGroup.setVisible(false);
								InfoBarChart.setVisible(true);
							}else{
								PieChartGroup.setVisible(false);
								InfoBarChart.setVisible(false);
							}
					}
					
				});
	}
	
	
	
	public void ShowEndHint(){
		this.Reciting.setDisable(true);
		this.EndHint.setVisible(true);
	}
	
	public void ShowMainPage(){
		this.main_page.setVisible(true);
		this.LexiconInfo.setVisible(false);
		this.ReciteSetting.setVisible(false);
		this.Reciting.setVisible(false);
		this.ShowMessageDialog.setVisible(false);
		this.EndHint.setVisible(false);
		this.RecitationRes.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	public void InitializeReciteSetting() throws IOException{
		List<String> values = Action.getInstance().search("");
		this.StartEntry0.setItems(FXCollections.observableList(values));
		this.StartEntry0.getEditor().setText("");
		this.MemoryWord.setText(Action.getInstance().getLastWord());
		this.ReciteNum.setText("");
		this.myToggleGroup.selectToggle(null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void InitializeReadLexiconInfo() throws IOException{
		
		LexiconInfo li = Action.getInstance().getLexiconInfo();
		LibraryInfo lis = Action.getInstance().getLexiconsInfo();

		this.LexiconName.setText(li.getName()+"�ʿ�");
		this.TotalCnt.setText(li.getCountTotal()+"");
		this.RightCnt.setText(li.getCountRight()+"");
		this.WrongCnt.setText(li.getCountWrong()+"");
		this.RecitedCnt.setText(li.getCountRecited()+"");
		this.Accuracy.setText(li.getAccuracy()+"%");

		
		ObservableList<PieChart.Data> pieChartData1 = FXCollections
				.observableArrayList(new PieChart.Data("�ѱ�����:"+li.getCountRecited(), li.getCountRecited()),
						new PieChart.Data("δ������:"+(li.getCountTotal()-li.getCountRecited()), li.getCountTotal()-li.getCountRecited()));
	/*ȫ���ʿ�*/
		ObservableList<PieChart.Data> pieChartData2 = FXCollections
				.observableArrayList(new PieChart.Data("�ѱ�����:"+lis.getCountRecited(), lis.getCompletionRateAll()),
						new PieChart.Data("δ������:"+(lis.getCountTotal()-lis.getCountRecited()), 100 - lis.getCompletionRateAll()));
		
		ObservableList<PieChart.Data> pieChartData3 = FXCollections
				.observableArrayList(new PieChart.Data("��ȷ������:"+li.getCountRight(), li.getCountRight()),
						new PieChart.Data("���󵥴���:"+li.getCountWrong(), li.getCountWrong()));
	
		ObservableList<PieChart.Data> pieChartData4 = FXCollections
				.observableArrayList(new PieChart.Data("��ȷ������:"+lis.getCountCorrect(), lis.getAccuracyAll()),
						new PieChart.Data("���󵥴���"+(lis.getCountRecited()-lis.getCountCorrect()), 100-lis.getAccuracyAll()));

		this.InfoPieChart1.setData(pieChartData1);
		this.InfoPieChart2.setData(pieChartData2);
		this.InfoPieChart3.setData(pieChartData3);
		this.InfoPieChart4.setData(pieChartData4);
	
		this.XAxis.setLabel("�ʿ���");
		this.YAxis.setTickUnit(1);
		this.YAxis.setLabel("�ٷֱ�/%");

		ObservableList<BarChart.Series> bcData = FXCollections.<BarChart.Series> observableArrayList();
		List<LexiconInfo> list = lis.getLexiconsInfo();
		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();

		for (int column = 0; column < list.size(); column++) {
			LexiconInfo lxi = list.get(column);
			series1.getData().add(new BarChart.Data(lxi.getName() + "�ʿ�", (double)lxi.getCountRecited()/lxi.getCountTotal()*100));
			series2.getData().add(new BarChart.Data(lxi.getName() + "�ʿ�", lxi.getAccuracy()));
		}

		series1.setName("������");
		series2.setName("��ȷ��");
		
		bcData.add(series1);
		bcData.add(series2);
		this.InfoBarChart.setBarGap(0.3);
		this.InfoBarChart.setCategoryGap(0.1);
		this.InfoBarChart.setData(bcData);
	}
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		LexiconDataBinding();
		RadioButtonSetting();
		ChartTypeSetting();
		this.ShowMainPage();
	}
}

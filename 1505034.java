/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtpsgsin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Date;

public class SMTPSemi {

    public static void main(String[] args) throws UnknownHostException, IOException, Exception {
        // String mailServer;
        //Scanner scn = new Scanner(System.in);
        //mailServer = scn.next();
        String[] tos1=new String[20];
        String[] tos = new String[20];
        String[] frm=null;
        String[] frm1=null;
        String jhamela;
        String mohajhamela = null;


        int m=0;
        String ln;
        String stri;
        Scanner scn = new Scanner(System.in);
        int n = 0;
        String mailServer = "webmail.buet.ac.bd";
        InetAddress mailHost = InetAddress.getByName(mailServer);
        InetAddress localHost = InetAddress.getLocalHost();
        Socket smtpSocket = new Socket(mailHost, 25);
        smtpSocket.setSoTimeout(20 * 1000);
        BufferedReader in = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
        PrintWriter pr = new PrintWriter(smtpSocket.getOutputStream(), true);
         String initialID=null;
        try {

            initialID = in.readLine();
            System.out.println(initialID);

        } catch (Exception e) {
            System.out.println("Time Out");
            return;
        }
          if (!initialID.startsWith("220")) {
                throw new Exception("220 reply not received from server.\n");
            } else {
                n = 1;
            }

        while (n < 9) {

            if (n == 1) {
                pr.println("HELO " + localHost.getHostName());
                  String welcome;
                try {
                     welcome = in.readLine();
                     System.out.println(welcome);
                } catch (Exception e) {
                    System.out.println("Time out");
                    return;
                }
                 if (!welcome.startsWith("250")) {
                        throw new Exception("250 reply not received from server.\n");

                    } else {
                        n = 2;
                    }
            }
            if (n == 2) {

                ln = scn.nextLine();
                pr.println(ln);
                 String welcome=null;
                try {
                     welcome = in.readLine();
                    System.out.println(welcome);
                } catch (Exception e) {
                    System.out.println("Time Out");
                    return;
                }
                    if (!welcome.startsWith("250")) {
                        System.out.println("please Enter FROM in correct format");
                    } else {
                         frm = ln.split("FROM:<", 2);
                         int k=1;
                          frm = frm[k].split(">", 2);
                          System.out.println(frm[k]);
                        n = 3;
                    }

            }

            if (n == 3) {

                String ln1;
                Scanner scn1 = new Scanner(System.in);

                ln1 = scn1.nextLine();
                pr.println(ln1);
                   String welcome;
                 //System.out.println("im here..");
                try {
                     welcome = in.readLine();
                    } catch (Exception e) {
                    System.out.println("Time Out");
                    return;
                }
                    if (!welcome.startsWith("250")) {
                        System.out.println("please enter RCPT To in correct format");
                    }
                    else{   int no=0;

                       frm1 = ln1.split("RCPT:<", 2);
                         int k=1;
                           String title = ln1.substring(9, ln1.length()-1);
                             System.out.println(title);
                             //m=1;
                             mohajhamela=title;
                             System.out.println(mohajhamela);
                             tos1[no]=title;
                             m++;
                             n = 4;
                    }
                    System.out.println(welcome);


                }

            if (n == 4) {

                ln = scn.nextLine();
                if (ln.startsWith("RCPT")) {
                    pr.println(ln);

                    // System.out.println("im here..");
                    String welcome=null;
                    try {
                         welcome = in.readLine();
                        System.out.println(welcome);
                    }
                    catch (Exception e) {
                        System.out.println("Time Out");
                        return;

                    }
                        if (!welcome.startsWith("250")) {
                            System.out.println("please enter RCPT To in correct format");
                        }
                        else{
                             String title = ln.substring(9, ln.length()-1);
                             System.out.println(title);
                             m=1;
                             tos[m]=title;
                             m++;
                        }
                    }
                else {
                    pr.println(ln);
                     String welc=null;
                     try{
                       welc = in.readLine();
                        System.out.println(welc);
                     }
                     catch(Exception e)
                     {
                         System.out.println("Time out");
                         return;
                     }
                        if (!welc.startsWith("354")) {
                            System.out.println("DATA pls");
                        } else {
                            n = 5;
                        }
                }
            }
            if (n == 5) {
                String msg = null;
                int j=1;
                System.out.println(frm[0]);
                System.out.println(tos1[0]);
                System.out.println("Subject: ");
                ln = scn.nextLine();
                int i = 1;
                String sub;

                sub = ln;
                 msg = "Subject: " + sub + "\n";
                msg=msg+"From: "+frm[0]+"\n";
                int me=0;
                msg=msg+"To: "+tos1[0]+"\n";
                int mmm=1;
                if(m>0)
                {
                    while(m>=mmm)
                    {
                        msg=msg+"Cc: "+tos[mmm]+"\n";
                        System.out.println(tos[mmm-1]);
                        mmm++;
                    }
                }
                msg=msg+"\n";
                while (!ln.equalsIgnoreCase(".")) {
                    ln = scn.nextLine();
                    msg = msg + ln + "\n";

                }

               msg = msg + "\n.";
                pr.println(msg);
                try {
                    String wel = in.readLine();
                    System.out.println(wel);

                } catch (Exception e) {
                    System.out.println("Time Out");
                    return;

                }
                 n = 6;
            }

            if (n == 6) {

                System.out.println("send another msg?");
                ln = scn.nextLine();
                if (ln.startsWith("yes")) {
                    n = 1;
                } else {
                    n = 10;
                }
            }


    }
}}

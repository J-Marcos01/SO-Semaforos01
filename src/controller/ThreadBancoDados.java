package controller;

import java.util.concurrent.Semaphore;

/*1) Um servidor com multiprocessamento recebe requisições que envolve realizar cálculos
e fazer transações com bancos de dados. Por ter uma quantidade grande de núcleos de
processamentos e threads, além de um bom algoritmo de escalonamento de threads,
enquanto as threads fazem cálculos, estes podem ocorrer simultaneamente, mas
quando se faz a transação no banco de dados, esta deve ser apenas uma thread por
vez. Considere um conjunto de threads com IDs definidas na própria aplicação com
números iniciando em 1 e incrementando de um em um. As threads tem comportamento
como segue:
a) Threads com ID dividido por 3 resultando em resto igual a um fazem as transações:
- Cálculos de 0,2 a 1,0 segundos
- Transação de BD por 1 segundo     (2x)
b) Threads com ID dividido por 3 resultando em resto igual a dois fazem as transações:
- Cálculos de 0,5 a 1,5 segundos
- Transação de BD por 1,5 segundo  (3x)
c) Threads com ID dividido por 3 resultando em resto igual a zero fazem as transações:
- Cálculos de 1 a 2 segundos
- Transação de BD por 1,5 segundo  (3x) 
*/
public class ThreadBancoDados extends Thread{
	
	private int idThread;
	private int tempoCalc;
	private int tempoTransacao;
	private static Semaphore semaforo;
	
	public ThreadBancoDados (int idThread,Semaphore semaforo)
	{
		this.idThread=idThread;
		this.semaforo=semaforo;	
		
	}

	
	public void run()
	{
		calc();
		try {
			semaforo.acquire();
			trans();			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			semaforo.release();
		}
		
	}


	private void calc() {
		
		if(idThread%3==1)
		{
			for(int i=0;i<2;i++)
			{
		tempoCalc=(int)((Math.random()*81)+20);
		System.out.println(("Thread :" + idThread + " realizou cálculos por: " + (double) tempoCalc / 100
				+ " segundos.\n"));
				try {
					sleep(tempoCalc);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
		}
		}
		else if(idThread%3==2)
		{
			for(int i=0;i<3;i++)
			{
			tempoCalc=(int)((Math.random()*101)+50);
			System.out.println(("Thread :" + idThread + " realizou cálculos por: " + (double) tempoCalc / 100
					+ " segundos.\n"));
			try {
				sleep(tempoCalc);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}

			}
		}
		
		for(int i=0;i<3;i++)
		{
		tempoCalc=(int)((Math.random()*101)+100);
		System.out.println(("Thread :" + idThread + " realizou cálculos por: " + (double) tempoCalc / 100
				+ " segundos.\n"));
		
			try {
				sleep(tempoCalc);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}

		}

		}
	private void trans() {

		if (idThread % 3 == 1) {
			tempoTransacao= 100;
			for (int i = 0; i < 2; i++) {
				System.out.println("A thread :" + idThread + " realizou transações por: " + (double) tempoTransacao / 100
						+ " segundos.\n");

				try {
					sleep(tempoTransacao);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (idThread % 3 == 2 || idThread % 3 == 0) {
			tempoTransacao = 150;
			for (int i = 0; i < 3; i++) {
				System.out.println("A thread :" + idThread + " realizou transações por: " + (double) tempoTransacao / 100
						+ " segundos.\n");

				try {
					sleep(tempoTransacao);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}

		}
	}
		
	}
	
	

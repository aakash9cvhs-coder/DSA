import java.util.*;

/* Event Node (Doubly Linked List) */
class Event {
    int id;
    String name;
    int seats;

    Event prev;
    Event next;

    Event(int id,String name,int seats){
        this.id=id;
        this.name=name;
        this.seats=seats;
    }
}

/* Hash Table with Separate Chaining */
class HashTable {

    LinkedList<String>[] table;

    HashTable(int size){
        table = new LinkedList[size];
        for(int i=0;i<size;i++)
            table[i]=new LinkedList<>();
    }

    int hash(String key){
        return key.length()%table.length;
    }

    void insert(String key){
        int index=hash(key);
        table[index].add(key);
    }

    boolean search(String key){
        int index=hash(key);
        return table[index].contains(key);
    }
}

/* Circular Queue */
class CircularQueue {

    int front=-1;
    int rear=-1;
    int size=5;
    String arr[]=new String[size];

    void enqueue(String data){

        if((rear+1)%size==front){
            System.out.println("Queue Full");
            return;
        }

        if(front==-1) front=0;

        rear=(rear+1)%size;

        arr[rear]=data;
    }

    void display(){

        if(front==-1){
            System.out.println("Queue Empty");
            return;
        }

        int i=front;

        while(true){

            System.out.println(arr[i]);

            if(i==rear) break;

            i=(i+1)%size;
        }
    }
}

public class UniEventsDSA {

    static Event head=null;
    static Stack<String> history=new Stack<>();
    static PriorityQueue<Integer> priority=new PriorityQueue<>();
    static CircularQueue waiting=new CircularQueue();
    static HashTable users=new HashTable(10);


    /* Add Event */
    static void addEvent(int id,String name,int seats){

        Event e=new Event(id,name,seats);

        if(head==null){
            head=e;
        }else{

            Event temp=head;

            while(temp.next!=null)
                temp=temp.next;

            temp.next=e;
            e.prev=temp;
        }

        priority.add(id);
    }

    /* Display Events */
    static void displayEvents(){

        Event temp=head;

        while(temp!=null){

            System.out.println(temp.id+" "+temp.name+" Seats:"+temp.seats);

            temp=temp.next;
        }
    }

    /* Linear Search */
    static Event search(int id){

        Event temp=head;

        while(temp!=null){

            if(temp.id==id)
                return temp;

            temp=temp.next;
        }

        return null;
    }

    /* Register Event */
    static void register(int id){

        Event e=search(id);

        if(e==null){
            System.out.println("Event not found");
            return;
        }

        if(e.seats>0){

            e.seats--;
            history.push(e.name);

            System.out.println("Registered: "+e.name);

        }else{

            waiting.enqueue("Student waiting for "+e.name);
            System.out.println("Added to waiting queue");
        }
    }

    /* Infix to Postfix using Stack */
    static String infixToPostfix(String exp){

        Stack<Character> stack=new Stack<>();
        String result="";

        for(char c:exp.toCharArray()){

            if(Character.isLetterOrDigit(c))
                result+=c;

            else if(c=='(')
                stack.push(c);

            else if(c==')'){

                while(!stack.isEmpty() && stack.peek()!='(')
                    result+=stack.pop();

                stack.pop();
            }
            else{

                while(!stack.isEmpty())
                    result+=stack.pop();

                stack.push(c);
            }
        }

        while(!stack.isEmpty())
            result+=stack.pop();

        return result;
    }

    public static void main(String[] args){

        Scanner sc=new Scanner(System.in);

        users.insert("admin@uni.edu");
        users.insert("student@uni.edu");

        addEvent(101,"Hackathon",50);
        addEvent(102,"AI Workshop",40);
        addEvent(103,"Dance Fest",60);
        addEvent(104,"Football",100);

        int choice;

        do{

            System.out.println("\n--- University Event Management System ---");
            System.out.println("1 View Events");
            System.out.println("2 Search Event");
            System.out.println("3 Register Event");
            System.out.println("4 Registration History");
            System.out.println("5 Waiting Queue");
            System.out.println("6 Priority Events");
            System.out.println("0 Exit");

            choice=sc.nextInt();

            switch(choice){

                case 1:
                    displayEvents();
                    break;

                case 2:

                    System.out.print("Enter Event ID: ");
                    int id=sc.nextInt();

                    Event e=search(id);

                    if(e!=null)
                        System.out.println("Found: "+e.name);
                    else
                        System.out.println("Event not found");

                    break;

                case 3:

                    System.out.print("Enter Event ID: ");
                    int rid=sc.nextInt();

                    register(rid);

                    break;

                case 4:

                    System.out.println("Registration History:");
                    while(!history.isEmpty())
                        System.out.println(history.pop());

                    break;

                case 5:

                    System.out.println("Waiting List:");
                    waiting.display();

                    break;

                case 6:

                    System.out.println("Priority Events:");
                    for(int p:priority)
                        System.out.println(p);

                    break;

                case 7:

                    System.out.print("Enter Infix Expression: ");
                    String exp=sc.next();

                    System.out.println("Postfix: "+infixToPostfix(exp));

                    break;
            }

        }while(choice!=0);
    }
}
package com.lrw.test;

class LinkImpl implements ILink{
    private Node head;
    private Node last;
    private int size;
    /**
     * 真正的火车车厢，负责数据存储
     */
    private class Node{
        private Node prev;
        private Object data;
        private Node next;
 
        public Node(Node prev, Object data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
 
    @Override
    public Object get(int index){
        if(!isIndex(index)){
            return null;
        }
        return node(index).data;
    }
 
    @Override
    public boolean add(Object data) {
        Node temp = this.last;
        Node newnode = new Node(temp,data,null);
        this.last = newnode;
        if(this.head == null){
            this.head = newnode;
        } else {
            temp.next = newnode;
        }
        this.size++;
        return true;
    }
 
    @Override
    public int contains(Object data) {
        if (data == null) {
            for (Node temp = this.head; temp != null; temp = temp.next) {
                int i = 0;
                if (temp.data == null) {
                    return i;
                }
                i++;
            }
        } else {
            for (Node temp = this.head; temp != null; temp = temp.next) {
                int i = 0;
                if (temp.data.equals(data)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }
 
    @Override
    public void remove(Object data) {
        if (data == null){
            for (Node temp = this.head; temp!=null; temp=temp.next){
                if (temp.data == null){
                    unLink(temp);
                }
            }
        } else{
            for (Node temp = this.head; temp!=null; temp=temp.next) {
                if (temp.data.equals(data)) {
                    unLink(temp);
                }
            }
        }
    }
 
    @Override
    public Object set(int index, Object newData) {
        if (!isIndex(index)){
            return null;
        }
        Node node = node(index);
        Object num = node.data;
        node.data = newData;
        return num;
    }
 
    @Override
    public void clear() {
        for (Node temp = head; temp != null; ){
            Node node = temp.next;
            temp.data = null;
            temp = temp.prev = temp.next = null;
            temp = node;
            this.size--;
        }
    }
 
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node temp = head; temp!=null; temp=temp.next){
            result[i++] = temp.data;
        }
        return result;
    }
 
    @Override
    public int size() {
        return this.size;
    }
 
    @Override
    public void printLink() {
        Object[] data = this.toArray();
        for(Object o: data){
            System.out.println(o);
        }
    }
 
    /**
     * 仅供本方法使用,根据指定索引取得具体结点
     * @param index
     * @return
     */
    private Node node(int index){
        if(index < (size>>1)){
            Node temp = this.head;
            for (int i = 0; i<index; i++){
                temp = temp.next;
            }
            return temp;
        }
        Node temp = this.last;
        for (int i = size-1; i<index; i--){
            temp = temp.prev;
        }
        return temp;
    }
 
    private boolean isIndex(int index){
        return index>=0 && index < size;
    }
 
    private Object unLink(Node x){
        Object elementData = x.data;
        Node prev = x.prev;
        Node next = x.next;
        if(prev == null){
            this.head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if(next == null){
            this.last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.data = null;
        this.size--;
        return elementData;
    }
}

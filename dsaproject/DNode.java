package dsaproject;

//please note that this code is different from the textbook code, because the data is encapsulated!

public class DNode<T>
{
    private T item;
    private DNode<T> next;
    private DNode<T> back;

    public DNode(T newItem)
    {
        item = newItem;
        next = this;
        back = this;
    } // end constructor

    public DNode(T newItem, DNode<T> nextNode, DNode<T> previousNode)
    {
        item = newItem;
        next = nextNode;
        back = previousNode;
    } // end constructor

    public void setItem(T newItem)
    {
        item = newItem;
    } // end setItem

    public T getItem()
    {
        return item;
    } // end getItem

    public void setNext(DNode<T> nextNode)
    {
        next = nextNode;
    } // end setNext

    public DNode<T> getNext()
    {
        return next;
    } // end getNext

    public void setBack(DNode<T> previousNode) {
        back = previousNode;
    }

    public DNode<T> getBack() {
        return back;
    }
} // end class Node

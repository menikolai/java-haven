package anon.data_structures;


/**
 * This class defines the Linked-List data-struceture that supports bi-directional, cyclic flow of control.
 __________________________________________________________________________________________________________________________________
 |        cell2                    cell1     cell2          cell3                                          cell1     cell2       \ | /
 ____|________________________       _________________________________                                      __________________________\ /_
 |    |   |            |       |<-----------o     |           |        |<--------............---------------------o     |          |       |
 cell1 |    o   |  Object o  |    o-------->|           |  Object o |    o------ .... total of n elements -------->|          | Object o |   o   |    <-- UnidirectionalLinkedListElement
 |________|____________|_______|      |___________|___________|________|            in the list              |__________|__________|___|___|
 index0     ^                                         index1                                                  index(n - 1)            |
 / \                                                                                                                        |
 |_________________________________________________________________________________________________________________________|
 *
 * The elements in such an instance of this class are linked together the way it has been graphically depicted above.
 *
 * Just like most other data-structures, the indexing begins from 0.
 *
 * @author saurabh000345
 * @since 1.0.1
 */
public class BidirectionalLinkedList<E>
{
    // instance variables
    private BidirectionalLinkedListElement<E> top, tail, pointing;      // first, last and currently pointing
    private int length = 0;                                             // size of the list

    /**
     *          cell1            cell2             cell3
     _____________________________________________________
     |                  |             |                    |
     |   Reference to   |    E o      |    Reference to    |
     |     previous     |             |        next        |
     |      element     |             |      element       |
     |__________________|_____________|____________________|
     *
     * element stores the entry of type E
     * Each instance stores an index value stored in the variable <index> and indexing begins from 0.
     *
     * @author saurabh000345
     * @since 1.0.1
     */
    private class BidirectionalLinkedListElement<E>
    {
        // instance variables
        private E element;                                          // stores the entry
        private BidirectionalLinkedListElement<E> next, prev;       // references to the next and previous elements
        private int index = 0;                                      // index value of the instance

        /**
         * Parameterized constructor to hold the element o of type E.
         * @param o The element to be stored.
         */
        private BidirectionalLinkedListElement(E o){
            element = o;            // element stored
            prev = this;            // cyclic; so previous and next is the same it will be configured later by the methods
            next = this;            // same as above
        }

        /**
         * Creates an element while assigning an index value to the element.
         * @param o The element to be stored
         * @param index The index value of the new object
         */
        private BidirectionalLinkedListElement(E o, int index){
            this(o);
            this.index = index;
        }

        /**
         * This method creates a new BidirectionalLinkedListElement and adds its reference to the current Element. It
         manages all configuration like maintaining the cyclic linking by correctly assigning the next reference of the
         newly created Element as that of the first element in the list(the last element will redirect to the first).
         * @param o  the element to be added
         */
        private void addElement(E o){
            BidirectionalLinkedListElement<E> element = new BidirectionalLinkedListElement<E>(o);
            element.setPrev(this);
            element.setNext(this.next);
            next = element;
            element.next().setPrev(element);
            element.setIndex(index + 1);
        }

        /**
         * This method changes the reference of the previous element to the arguement itself.
         * @param e  the desired next element
         */
        private void setPrev(BidirectionalLinkedListElement<E> e){
            prev = e;
        }

        /**
         * This method changes the reference of the next element to the arguement itself.
         * @param e  the desired next element
         */
        private void setNext(BidirectionalLinkedListElement<E> e){
            next = e;
        }

        /**
         * This method updates the entry(stored element) contained in this element.
         * @param o  new entry.
         */
        private void setEntry(E o){
            element = o;
        }

        /**
         * This method updates the index value of the current element.
         * @param index  the new index value.
         */
        private void setIndex(int index){
            this.index = index;
        }

        /**
         * @return the index value of the invoking element.
         */
        private int getIndex(){
            return index;
        }

        /**
         * @return the reference(AND NOT THE CLONE!!) of the entry stored in the invoking element.
         */
        private E getRefEntry(){
            return element;
        }

        /**
         * This method returns the reference of the next element in the list. Here, we must remember that the
         list is basically cyclic. Thus, if this method is invoked by the last element of the list, it will return a
         reference to the first element in the list.
         */
        private BidirectionalLinkedListElement<E> next(){
            return next;
        }

        /**
         * This method returns the reference of the previous element in the list. Here, we must remember that the
         list is basically cyclic. Thus, if this method is invoked by the first element of the list, it will return a
         reference to the last element in the list.
         */
        private BidirectionalLinkedListElement<E> prev(){
            return prev;
        }

        /**
         * @return the String representation of the entry contained in the invoking Element.
         */
        @Override
        public String toString(){
            return element.toString();
        }
    }

    /**
     * The no-arg constructor to create an empty list.
     */
    public BidirectionalLinkedList(){

    }

    /**
     * This constructor creates a new list with only one element that is being passed as argument.
     * @param o The element to be contained in the list.
     */
    public BidirectionalLinkedList(E o){
        top = new BidirectionalLinkedListElement<E>(o);         // its the first element in this brand-new list!
        pointing = top;                                         // the pointer will be pointing to it
        tail = top;                                             // its the first and also the last element for now
    }

    /**
     * @param index  the index of the element to be retrieved.
     * @return the entry contained at the element at the specified index.
     */
    public E getElement(int index) throws ListIndexOutOfBoundsException{
        return  get(index).getRefEntry();
    }

    /**
     * @param index  the index of the UnidirectionalLinkedListElement<E> to be retrieved.
     * @return the UnidirectionalLinkedListElement<E> at the specified index.
     * We must rememeber here that UnidirectionalLinkedListElement<E> has private access and thus none of its methods except toString()
    can be accessed outside this class.
     * If you want to get the entry E at the specifiedIndex, use getElement(int) method instead.
     */
    public BidirectionalLinkedListElement<E> get(int index) throws ListIndexOutOfBoundsException{
        if(index >= length) throw new ListIndexOutOfBoundsException();          // Invalid index
        index = (index + length)%length;                                        // Thus, entertains negative values
        BidirectionalLinkedListElement<E> pointing = top;                       // new varibale
        for(int i = 0; i < index; i++){                                         // reaching the specified indexx
            pointing = pointing.next();
        }
        return pointing;
    }

    /**
     * This method adds an element at the end of the list.
     * @param o  the entry to be added to the new element.
     */
    public void add(E o){
        if(length == 0){                        // if the list is empty, a different approach is needed
            top = new BidirectionalLinkedListElement<E>(o);
            pointing = top;
            tail = top;
        } else{
            tail.addElement(o);
            tail = tail.next();
        }
        length++;                               // Updating the length.
    }

    /**
     * This method adds a new element in the MIDDLE(not the end) of the list such that the new element has <index> = index
     * @param index  the index value of the new element.
     * @param o  entry to be contained in the new element
     */
    public void add(E o, int index) throws ListIndexOutOfBoundsException{
        if(index >= length) throw new ListIndexOutOfBoundsException();          // invalid index
        BidirectionalLinkedListElement<E> e = new BidirectionalLinkedListElement<E>(o, index);      // new element created with index
        BidirectionalLinkedListElement<E> curr = get(index);                    // begining to update the indices
        BidirectionalLinkedListElement<E> point = curr;
        for(int i = index; i < length; i++){
            point.setIndex(i + 1);
            point = point.next();
        }
        get(index - 1).setNext(e);          // adding the element to the list
        e.setPrev(get(index - 1));
        e.setNext(curr);
        curr.setPrev(e);
        length++;                           // updating the size
    }

    /**
     * Removes the element at index <index> in the list.
     * @param index  the index of the element which has to be removed.
     */
    public void remove(int index) throws ListIndexOutOfBoundsException{
        if(index >= length) throw new ListIndexOutOfBoundsException();              // Invalid index
        get(index - 1).setNext(get(index).next());                                  // removing it from reference
        get(index - 1).next().setPrev(get(index - 1));
        for(int i = index + 1; i < length; i++){        // updating the index values
            get(i).setIndex(i - 1);
        }
        length--;                       // updating size
        System.gc();                    // requesting for garbage cleaner
    }


    /**
     * This method updates the entry in the element at index <index> and sets it to the passed argument.
     * @param o  the new entry
     * @param index  the index of the element to be updated
     */
    public void setEntry(E o, int index){
        get(index).setEntry(o);
    }

    /**
     * @return the reference to the next element while updating the pointer.
     */
    public BidirectionalLinkedListElement<E> prev(){
        pointing = pointing.prev();
        return pointing;
    }

    /**
     * @return the reference to the next element while updating the pointer.
     */
    public BidirectionalLinkedListElement<E> next(){
        pointing = pointing.next();
        return pointing;
    }

    /**
     * @return the String representation of the entries stored in the list in format "[ ob1.toString(), ob2.toString ... ]"
     */
    @Override
    public String toString() throws ListIndexOutOfBoundsException{
        String ret = "[ " + get(0);
        for(int i = 1; i < length; i++){
            ret += ", " + get(i).toString();
        }
        return ret + " ]";
    }
}

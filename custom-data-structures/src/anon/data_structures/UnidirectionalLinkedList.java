package anon.data_structures;

/**
 * This is a class for the Linked List data-structure that supports only unidirectional, cyclic flow of control.
 cell1      cell2           cell1     cell2                                           cell1     cell2
 ____________________      _____________________                                      ___________________
 |           |        |     |           |        |                                     |          |        |
 | Object o  |    o-------->|  Object o |    o------ .... total of n elements.... ---->| Object o |    o   |   <-- UnidirectionalLinkedListElement
 |___________|________|     |___________|________|            in the list              |__________|____|___|
 index0     ^                   index1                                                  index(n - 1)  |
 / \                                                                                        |
 |_________________________________________________________________________________________|

 * The elements are linked to each-other the way it is graphed above. Each element is linked to the next element.
 *
 * Just like other data structures, this class begins indexing its UnidirectionalLinkedListElement s from 0 such that a list
 of n elements just as the one graphed above, has the index: (n - 1) as the last index.
 *
 * @author saurabh000345
 * @since 1.0.1
 */
public class UnidirectionalLinkedList<E>
{
    // instance variables
    private UnidirectionalLinkedListElement<E> top;             // the first element in the list
    private UnidirectionalLinkedListElement<E> pointing;        // the element that the cursor is now on.
    private UnidirectionalLinkedListElement<E> tail;            // the last element of the list
    private int length = 0;                                     // total number of elements in the list

    /**
     * This class defines each of the element to be stored in the list.

     cell 1             cell2
     ___________________________________________________________
     |              |                                            |
     |   E cell1    |   UnidirectionalLinkedListElement cell2    |
     |______________|____________________________________________|
     *
     * cell1 stores the data while cell2 stores the reference to the next element in the list.
     * Each instance stores its index and indexing begins from 0.
     *
     * @author saurabh000345
     * @since 1.0.1
     */
    private class UnidirectionalLinkedListElement<E>
    {
        // instance variables
        private E cell1;                                            // to store the data
        private UnidirectionalLinkedListElement<E> cell2;           // to store the reference to the next element
        public int index = 0;

        /**
         * This constructor creates a new element storing data of type E.
         * @param o  the data to be stored.
         */
        private UnidirectionalLinkedListElement(E o){
            cell1 = o;
            cell2 = this;
        }

        /**
         * This constructor creates a new element and assigns an index to it.
         * @param o  the element to be stored.
         * @param index  the index of this UnidirectionalLinkedListElement.
         */
        private UnidirectionalLinkedListElement(E o, int index){
            this(o);
            this.index = index;
        }

        /**
         * This method creates a new UnidirectionalLinkedListElement and adds its reference to the current Element. It
         manages all configuration like maintaining the cyclic linking by correctly assigning the next reference of the
         newly created Element as that of the first element in the list.
         * @param o  the element to be added
         */
        private void addElement(E o){
            UnidirectionalLinkedListElement e = new UnidirectionalLinkedListElement(o, index + 1);      // new element created with the correct index
            e.setRef(cell2);                // the reference of to the currently next element(before adding the element) is passed over to the new element.
            cell2 = e;                      // the element is added to the list. this refers to the new element.
        }

        /**
         * This method removes the next element from reference, effectually removing it from the list.
         * The task of changing the index values of the constituent elements is managed in the container class.
         */
        private void removeNextElement(){
            cell2 = next().next();
            int index = this.index;
        }

        /**
         * This method updates the entry(stored element) contained in this element.
         * @param o  new entry.
         */
        private void setEntry(E o){
            cell1 = o;
        }

        /**
         * This method changes the reference of the next element to the arguement itself.
         * @param e  the desired next element
         */
        private void setRef(UnidirectionalLinkedListElement<E> e){
            cell2 = e;
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
            return cell1;
        }

        /**
         * This method returns the reference of the next element in the list. Here, we must remember that the
         list is basically cyclic. Thus, if this method is invoked by the last element of the list, it will return a
         reference to the first element in the list.
         */
        private UnidirectionalLinkedListElement<E> next(){
            return cell2;
        }

        /**
         * @return the String representation of the entry contained in the invoking Element.
         */
        @Override
        public String toString(){
            return cell1.toString();
        }
    }

    /**
     * The no-arg constructor to create an empty list.
     */
    public UnidirectionalLinkedList(){

    }

    /**
     * This constructor creates a new list with only one element that is being passed as argument.
     * @param o The element to be contained in the list.
     */
    public UnidirectionalLinkedList(E o){
        top = new UnidirectionalLinkedListElement(o);   // if there is only one element in the list, it is the first and last element.
        pointing = top;                                 // the pointer will be pointing to the first element
        tail = top;
        length++;                                       // updating the size of the list.
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
    public UnidirectionalLinkedListElement<E> get(int index) throws ListIndexOutOfBoundsException{
        if(index >= length) throw new ListIndexOutOfBoundsException();          // if the index is out of bounds.
        index = (index + length)%length;                                        // this will enable users to pass
        //negative values.... I have used it in other methods too...
        UnidirectionalLinkedListElement<E> pointing = top;                      // sets pointing to the first element.
        // this is the local variable and must not be confused with the instance variable
        for(int i = 0; i < index; i++){
            pointing = pointing.next();
        }
        return pointing;
    }

    /**
     * This method adds an element at the end of the list.
     * @param o  the entry to be added to the new element.
     */
    public void add(E o){
        if(length == 0){                                            // if the list if empty, tail will have null so a
            //different approach is needed
            top = new UnidirectionalLinkedListElement<E>(o);        // mechanism used in the single argument constructor.
            tail = top;
            pointing = top;
        } else{
            tail.addElement(o);
            tail = tail.next();                                     // tail is being updated
        }
        length++;                                                   // size is updated
    }

    /**
     * This method adds a new element in the MIDDLE(not the end) of the list such that the new element has <index> = index
     * @param index  the index value of the new element.
     * @param o  entry to be contained in the new element
     */
    public void add(E o, int index) throws ListIndexOutOfBoundsException{
        if(index >= length) throw new ListIndexOutOfBoundsException();
        UnidirectionalLinkedListElement<E> point = get(index);      // current element at index <index>
        get(index - 1).addElement(o);                               // new element added at index <index>
        point.setIndex(index + 1);                                  // index of point updated
        while(point.getIndex() < length){                           // updating the index values of the other objects
            point.setIndex(point.getIndex() + 1);
            point = point.next();
        }
        length++;                                                   // incrementing the size of the list
    }

    /**
     * Removes the element at index <index> in the list.
     * @param index  the index of the element which has to be removed.
     */
    public void remove(int index){
        get(index - 1).removeNextElement();
        for(int i = index + 1; i < length; i++){            // updating the index values of the other elements.
            get(i).setIndex(i - 1);
        }
        length--;                                           // decrementing the size of the list
    }

    /**
     * This method updates the entry in the current element and sets it to the passed argument.
     * @param o  the new entry
     */
    public void setEntry(E o){
        pointing.setEntry(o);
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
    public UnidirectionalLinkedListElement<E> next(){
        pointing = pointing.next();
        return pointing;
    }

    /**
     * @return the String representation of the entries stored in the list in format "[ ob1.toString(), ob2.toString ... ]"
     */
    @Override
    public String toString(){
        String ret = "[ ";
        for(int i = 0; i < length; i++){
            ret += get(i).toString() + ((i == length - 1)?"":", ");
        }
        return ret + " ]";
    }
}

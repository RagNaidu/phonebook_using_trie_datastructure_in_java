import java.util.ArrayList;
import java.util.HashMap;

class trienode {
    HashMap<Character, trienode> child;
    boolean isLast;

    public trienode() {
        child = new HashMap<Character, trienode>();
        for (char i = 'a'; i <= 'z'; i++)
            child.put(i, null);
        isLast = false;
    }
}

// phonebook directory
class Directory {
    HashMap<String, Integer> phonebook;
    Trie trie;

    public Directory() {
        phonebook = new HashMap<>();
        trie = new Trie();
    }

    public void addContact(String name, Integer phoneno) {
        phonebook.put(name, phoneno);
        trie.insert(name);
    }

    public void Combinations(String query) {
        trie.getContacts(query, phonebook);
    }
}

class Trie {
    trienode root;

    public Trie() {
        root = new trienode();
    }

    public void insert(String s) {
        int len = s.length();
        trienode itr = root;
        for (int i = 0; i < len; i++) {
            trienode nextNode = itr.child.get(s.charAt(i));
            if (nextNode == null) {
                nextNode = new trienode();
                itr.child.put(s.charAt(i), nextNode);
            }
            itr = nextNode;
            if (i == len - 1) {
                itr.isLast = true;
            }
        }
    }

    public void displayContactsUtil(trienode curNode,
            String prefix, ArrayList<String> contactsWithPrefix) {
        if (curNode.isLast) {
            contactsWithPrefix.add(prefix);
        }
        for (char i = 'a'; i <= 'z'; i++) {
            trienode nextNode = curNode.child.get(i);
            if (nextNode != null) {
                displayContactsUtil(nextNode, prefix + i, contactsWithPrefix);
            }
        }
    }

    public void getContacts(String str, HashMap<String, Integer> phonebook) {
        trienode prevNode = root;
        String prefix = "";
        int len = str.length();
        int i;
        for (i = 0; i < len; i++) {
            prefix += str.charAt(i);
            char lastChar = prefix.charAt(i);
            trienode curNode = prevNode.child.get(lastChar);
            if (curNode == null) {
                i++;
                break;
            }
            ArrayList<String> contactsWithPrefix = new ArrayList<>();
            displayContactsUtil(curNode, prefix, contactsWithPrefix);
            for (String contact : contactsWithPrefix) {
                if (contact.startsWith(str)) {
                    System.out.println("Contact number of " + contact + " is: " + phonebook.get(contact));
                }
            }
            prevNode = curNode;
        }
    }
}

class PhoneBook {
    public static void main(String args[]) {
        Directory directory = new Directory();
        directory.addContact("Rahul", 898485565);
        directory.addContact("Ram", 589542444);
        directory.addContact("Ramgopal", 76483423);
        directory.addContact("don", 988232323);
        directory.addContact("Ramhari", 849383432);
        directory.addContact("dongli", 978933323);
        directory.addContact("donsenu", 92348726);

        String query = "Ram";
        directory.Combinations(query);
    }
}

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

fun main(args: Array<String>) {
    /* Set up frame */
    val frame = JFrame().apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "Paper Doll"
        setSize(800, 600)
        isVisible = true
        isResizable = false
    }

    /* Set up menu bar */
    val menuBar = JMenuBar().also {
        frame.jMenuBar = it
    }

    /* Set up file menu */
    val file = JMenu("File").also {
        menuBar.add(it)
    }

    val reset = JMenuItem("Reset").apply {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK)
    }.also {
        file.add(it)
    }

    file.add(JSeparator())

    val quit = JMenuItem("Quit").apply {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)
        addActionListener { e: ActionEvent -> System.exit(0) }
    }.also {
        file.add(it)
    }

    /* Set up ragdoll menu */
    val ragdoll = JMenu("Ragdoll").also {
        menuBar.add(it)
    }

    //Person
    val person = JRadioButtonMenuItem("Person", true).also {
        ragdoll.add(it)
    }

    //Tree
    val tree = JRadioButtonMenuItem("Tree").also {
        ragdoll.add(it)
    }

    //Dog
    val dog = JRadioButtonMenuItem("Dog").also {
        ragdoll.add(it)
    }

    ButtonGroup().apply {
        add(person)
        add(tree)
        add(dog)
    }

    /* Set up canvas */
    val canvas = Canvas().also {
        frame.add(it)
    }

    reset.addActionListener { canvas.reset() }
    person.addActionListener { canvas.setRagdoll(Ragdoll.Type.PERSON) }
    tree.addActionListener { canvas.setRagdoll(Ragdoll.Type.TREE) }
    dog.addActionListener { canvas.setRagdoll(Ragdoll.Type.DOG) }

    frame.apply {
        repaint()
        revalidate()
    }
}

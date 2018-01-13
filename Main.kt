import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        /* Set up frame */
        val frame = JFrame()
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.title = "Paper Doll"
        frame.setSize(800, 600)
        frame.isVisible = true
        frame.isResizable = false

        /* Set up menu bar */
        val menuBar = JMenuBar()
        frame.jMenuBar = menuBar

        /* Set up file menu */
        val file = JMenu("File")
        menuBar.add(file)

        val reset = JMenuItem("Reset")
        reset.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK)
        file.add(reset)

        file.add(JSeparator())

        val quit = JMenuItem("Quit")
        quit.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)
        quit.addActionListener { e: ActionEvent -> System.exit(0) }
        file.add(quit)

        /* Set up ragdoll menu */
        val ragdoll = JMenu("Ragdoll")
        menuBar.add(ragdoll)

        //Person
        val person = JRadioButtonMenuItem("Person", true)
        ragdoll.add(person)

        //Tree
        val tree = JRadioButtonMenuItem("Tree")
        ragdoll.add(tree)

        //Dog
        val dog = JRadioButtonMenuItem("Dog")
        ragdoll.add(dog)

        val group = ButtonGroup()
        group.add(person)
        group.add(tree)
        group.add(dog)

        /* Set up canvas */
        val canvas = Canvas()
        frame.add(canvas)

        reset.addActionListener { e: ActionEvent -> canvas.reset() }
        person.addActionListener { e: ActionEvent -> canvas.setRagdoll(Ragdoll.Type.PERSON) }
        tree.addActionListener { e: ActionEvent -> canvas.setRagdoll(Ragdoll.Type.TREE) }
        dog.addActionListener { e: ActionEvent -> canvas.setRagdoll(Ragdoll.Type.DOG) }

        frame.repaint()
        frame.revalidate()
    }
}

const AddTask = () => {
    const addTaskHandler = (e) => {
        e.preventDefault()
        console.log("hello world")

        }

    return(
        <div>
            <form className="d-flex justify-content-center pt-3 ">
                <div>
                    <input id="input" className="rounded-3 p-2" placeholder="add task..." />
                </div>
                <button className="btn btn-primary" onClick={addTaskHandler}>add Task</button>
            </form>
        </div>
    )


}



export default AddTask
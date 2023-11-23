const commentBox = document.getElementById("commentBox");
commentBox.style.display = "none";

function toggleCommentBox() {
    const commentBox = document.getElementById("commentBox");
    if (commentBox.style.display === "none") {
        commentBox.style.display = "block";
    } else {
        commentBox.style.display = "none";
    }
}
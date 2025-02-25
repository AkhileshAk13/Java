// Sample News Data
const newsData = [
    { title: "Tech News 1", content: "Latest Tech Updates...", category: "Technology" },
    { title: "Sports News 1", content: "Sports Highlights Today...", category: "Sports" },
    { title: "Political News 1", content: "Government New Policies...", category: "Politics" }
];

const newsContainer = document.getElementById("news-container");

// Load News
function loadNews() {
    newsContainer.innerHTML = "";
    newsData.forEach((news, index) => {
        const newsCard = document.createElement("div");
        newsCard.classList.add("news-card");
        newsCard.dataset.category = news.category;
        newsCard.innerHTML = <h3>${news.title}<p>${news.content.substring(0, 50)}...</p></h3>;
        newsCard.addEventListener("click", () => openModal(news));
        newsContainer.appendChild(newsCard);
    });
}

// Filter News by Search
function filterNews() {
    const searchValue = document.getElementById("search").value.toLowerCase();
    document.querySelectorAll(".news-card").forEach(card => {
        card.style.display = card.innerText.toLowerCase().includes(searchValue) ? "block" : "none";
    });
}

// Filter News by Category
function filterCategory(category) {
    document.querySelectorAll(".news-card").forEach(card => {
        card.style.display = (category === "All" || card.dataset.category === category) ? "block" : "none";
    });
}

// Open Modal
const modal = document.getElementById("news-modal");
const modalTitle = document.getElementById("modal-title");
const modalContent = document.getElementById("modal-content");
function openModal(news) {
    modal.style.display = "flex";
    modalTitle.textContent = news.title;
    modalContent.textContent = news.content;
}

// Close Modal
document.querySelector(".close").addEventListener("click", () => {
    modal.style.display = "none";
});

// Dark Mode Toggle
document.getElementById("theme-toggle").addEventListener("click", () => {
    document.body.classList.toggle("dark-mode");
});

// Load Initial News
loadNews();
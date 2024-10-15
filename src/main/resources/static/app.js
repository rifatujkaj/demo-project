// Define the API URL
const apiUrl = 'http://localhost:8080/api/products';

// Fetch all products and render them in the DOM
async function fetchProducts() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const products = await response.json();
        renderProducts(products); // Render products on the page
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}


function renderProducts(products) {
    const productList = document.getElementById('product-list');
    productList.innerHTML = ''; // Clear any previous content

    if (products.length === 0) {
        productList.innerHTML = '<li>No products available</li>';
        return;
    }

    products.forEach(product => {
        const productItem = document.createElement('li');
        productItem.innerHTML = `
            <span class="product-attribute product-name">${product.name}</span>
            <span class="product-attribute product-brand">${product.brandId}</span>
            <span class="product-attribute product-portion">${product.portion}</span>
            <span class="product-attribute product-price">$${product.price.toFixed(2)}</span>
            <button class="delete-button" onclick="deleteProduct(${product.id})">Delete</button>`;
        productList.appendChild(productItem);
    });

}



// Create a new product
async function createProduct(product) {
    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(product),
        });

        if (!response.ok) {
            throw new Error(`Failed to create product. Status: ${response.status}`);
        }

        const newProduct = await response.json();
        console.log('Product created:', newProduct);
        fetchProducts(); // Refresh the product list after adding a new one

    } catch (error) {
        console.error('Error creating product:', error);
    }
}

// Delete a product
async function deleteProduct(productId) {
    try {
        const response = await fetch(`${apiUrl}/${productId}`, {
            method: 'DELETE',
        });

        if (!response.ok) {
            throw new Error(`Failed to delete product. Status: ${response.status}`);
        }

        console.log(`Product with ID ${productId} deleted successfully.`);
        fetchProducts(); // Refresh the product list after deleting

    } catch (error) {
        console.error('Error deleting product:', error);
    }
}


function showProductForm() {
    const productForm = document.getElementById('product-form');
    const productNameInput = document.getElementById('product-name');

    // Check if the form is currently visible
    if (productForm.style.display === 'none' || productForm.style.display === '') {
        // Show the form
        productForm.style.display = 'block';
    } else {
        // Hide the form and clear the inputs
        productForm.style.display = 'none';
        productNameInput.value = ''; // Clear the product name input
        document.getElementById('product-price').value = ''; // Clear the price input
        document.getElementById('product-category').value = ''; // Clear the category ID input
        document.getElementById('product-brand').value = ''; // Clear the brand ID input
        document.getElementById('product-portion').value = ''; // Clear the portion input
        document.getElementById('product-image').value = ''; // Clear the image URL input
    }
}

// Initially hide the product form
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('product-form').style.display = 'none'; // Hide form on load
});


function submitProduct() {
    const name = document.getElementById('product-name').value;
    const price = parseFloat(document.getElementById('product-price').value);
    const categoryId = parseInt(document.getElementById('product-category').value);
    const brandId = parseInt(document.getElementById('product-brand').value);
    const portion = document.getElementById('product-portion').value;
    const image = document.getElementById('product-image').value;

    if (name && price && categoryId && brandId && portion && image) {
        const newProduct = { name, price, categoryId, brandId, portion, image };
        createProduct(newProduct); // Send the new product to the server
        document.getElementById('product-form').style.display = 'none'; // Hide the form
    } else {
        alert('Please provide valid product details');
    }
}


// Call the function on page load
window.onload = fetchProducts;

/**
 * FreeFlow Common JS Functions
 * Handles shared functionality across all pages
 * Traditional navigation model (no SPA)
 */

// Global app variable
let app;

/**
 * Initializes the application with theme support and includes
 * @param {Object} options - Configuration options
 * @param {boolean} options.includeHeader - Whether to include the header
 * @param {boolean} options.includeSidebar - Whether to include the sidebar
 * @param {string} options.activeMenu - ID of the active menu item
 */
function initApp(options = {}) {
  // Default options
  const defaults = {
    includeHeader: true,
    includeSidebar: true,
    activeMenu: null
  };
  
  // Merge with defaults
  const config = {...defaults, ...options};
  
  // Initialize Vue app
  app = new Vue({
    el: '#app',
    data: {
      theme: localStorage.getItem('centaura-theme') || 'light',
      currentPage: window.location.pathname.split('/').pop() || 'index.html'
    },
    computed: {
      mainContentStyle() {
        return {
          paddingTop: '50px',
          marginLeft: document.body.classList.contains('sidebar-hidden') ? '0' : '220px',
          transition: 'margin-left 0.2s'
        };
      }
    },
    mounted() {
      document.body.className = this.theme;
      
      // Load includes
      if (config.includeHeader) {
        loadHeader(config);
      }
      
      if (config.includeSidebar) {
        loadSidebar(config.activeMenu);
      }
    }
  });
  
  // Make app accessible globally
  window.freeflowApp = app;
  window.app = app; // Also maintain the app variable for backward compatibility
  
  return app;
}

/**
 * Loads the header HTML and sets up theme toggle
 * @param {Object} config - Configuration options or Vue instance
 */
function loadHeader(config) {
  // Determine theme source - either from global app or passed Vue instance
  const themeSource = (config && config.theme) ? config : (window.app || {});
  const theme = themeSource.theme || localStorage.getItem('centaura-theme') || 'light';
  
  fetch('header.html')
    .then(r => r.text())
    .then(html => {
      document.getElementById('header-container').innerHTML = html;
      
      // Set header theme
      const header = document.getElementById('main-header');
      if (header) {
        header.className = 'navbar navbar-expand-lg fixed-top shadow-sm ' + 
          (theme === 'dark' ? 'navbar-dark bg-dark' : 'navbar-light bg-light');
        
        // Set icon
        const icon = document.getElementById('theme-toggle-icon');
        if (icon) icon.innerHTML = theme === 'light' ? '🌙 Escuro' : '☀️ Claro';
      }
      
      // Theme toggle
      document.getElementById('theme-toggle').onclick = function() {
        // Get the current theme from localStorage (most reliable source)
        const currentStoredTheme = localStorage.getItem('centaura-theme') || 'light';
        // Toggle to the new theme
        const newTheme = currentStoredTheme === 'light' ? 'dark' : 'light';
        
        // Update localStorage
        localStorage.setItem('centaura-theme', newTheme);
        
        // Update document body
        document.body.className = newTheme;
        
        // Update theme in the Vue instance if it was passed
        if (themeSource && typeof themeSource === 'object') {
          if (themeSource.$data && themeSource.$data.theme !== undefined) {
            themeSource.$data.theme = newTheme;
          } else if (themeSource.theme !== undefined) {
            themeSource.theme = newTheme;
          }
        }
        
        // Update global app if it exists
        if (window.app && window.app.theme !== undefined) {
          window.app.theme = newTheme;
        }
        
        // Dispatch a custom event for Vue components to listen to
        window.dispatchEvent(new CustomEvent('themeChanged', { 
          detail: { theme: newTheme } 
        }));
        
        // Update sidebar theme
        const sidebar = document.getElementById('sidebar-menu');
        if (sidebar) {
          sidebar.classList.toggle('dark', newTheme === 'dark');
        }
        
        // Update header theme
        if (header) {
          header.className = 'navbar navbar-expand-lg fixed-top shadow-sm ' + 
            (newTheme === 'dark' ? 'navbar-dark bg-dark' : 'navbar-light bg-light');
          
          const icon = document.getElementById('theme-toggle-icon');
          if (icon) icon.innerHTML = newTheme === 'light' ? '🌙 Escuro' : '☀️ Claro';
        }
      };
      
      // Hamburger menu (wait for sidebar)
      function attachSidebarToggle() {
        var btn = document.getElementById('menu-toggle');
        var sidebar = document.getElementById('sidebar-menu');
        if (btn && sidebar) {
          btn.onclick = function() {
            sidebar.classList.toggle('d-none');
            document.body.classList.toggle('sidebar-hidden');
            
            // Trigger resize event to update layouts
            window.dispatchEvent(new Event('resize'));
          };
        } else {
          setTimeout(attachSidebarToggle, 100);
        }
      }
      
      attachSidebarToggle();
    });
}

/**
 * Loads the sidebar HTML and sets active menu item
 * @param {string} activeMenuId - ID of the active menu item
 */
function loadSidebar(activeMenuId = null) {
  // Determine theme from localStorage
  const theme = localStorage.getItem('centaura-theme') || 'light';
  
  fetch('sidebar.html')
    .then(r => r.text())
    .then(html => {
      document.getElementById('sidebar-container').innerHTML = html;
      
      // Set theme class
      const sidebar = document.getElementById('sidebar-menu');
      if (sidebar) {
        sidebar.classList.toggle('dark', theme === 'dark');
      }
      
      // Set active menu item
      if (activeMenuId && document.getElementById(activeMenuId)) {
        document.getElementById(activeMenuId).classList.add('active');
      }
    });
}

/**
 * Updates active menu item based on current page
 * @param {string} page - The current page
 */
function updateActiveMenuItem(page) {
  // Remove active class from all menu items
  const menuItems = document.querySelectorAll('#sidebar-menu .nav-link');
  menuItems.forEach(item => {
    item.classList.remove('active');
  });
  
  // Add active class to current menu item
  const currentMenuItem = Array.from(menuItems).find(item => 
    item.getAttribute('href') === page);
  
  if (currentMenuItem) {
    currentMenuItem.classList.add('active');
  }
}

/**
 * Updates active menu item based on current page
 * @param {string} page - The current page
 */
function updateActiveMenuItem(page) {
  // Remove active class from all menu items
  const menuItems = document.querySelectorAll('#sidebar-menu .nav-link');
  menuItems.forEach(item => {
    item.classList.remove('active');
  });
  
  // Add active class to current menu item
  const currentMenuItem = Array.from(menuItems).find(item => 
    item.getAttribute('href') === page);
  
  if (currentMenuItem) {
    currentMenuItem.classList.add('active');
  }
}

/**
 * Helper function to ensure a script is loaded
 * @param {string} src - The source URL of the script to load
 * @returns {Promise} - Promise that resolves when the script is loaded
 */
function ensureScriptLoaded(src) {
  return new Promise((resolve, reject) => {
    // Check if script is already loaded
    if (document.querySelector(`script[src="${src}"]`)) {
      resolve();
      return;
    }
    
    // Load the script
    const script = document.createElement('script');
    script.src = src;
    script.onload = () => {
      resolve();
    };
    script.onerror = (err) => {
      console.error(`Error loading script ${src}:`, err);
      reject(err);
    };
    document.head.appendChild(script);
  });
}

// Export functions to global scope for direct access from pages
window.loadHeader = loadHeader;
window.loadSidebar = loadSidebar;
window.updateActiveMenuItem = updateActiveMenuItem;
